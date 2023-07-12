package space.rodionov.porosenokpetr.core.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.data.local.entity.CategoryEntity
import space.rodionov.porosenokpetr.core.data.local.entity.CategoryWithWords
import space.rodionov.porosenokpetr.core.data.local.entity.WordEntity

@Dao
interface WordDao {

    @Query("SELECT * FROM wordentity WHERE categoryName IN (SELECT name FROM categoryentity WHERE isCategoryActive = 1) AND wordStatus = 1 ORDER BY RANDOM() LIMIT 10")
    suspend fun getTenWords(): List<WordEntity>

    @Query("SELECT * FROM wordentity")
    suspend fun getAllWords(): List<WordEntity>

    @Query("SELECT * FROM categoryentity")
    fun observeAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categoryentity")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT * FROM wordentity WHERE categoryName IN (:categoryNames) AND (rus LIKE '%' || :searchQuery || '%' OR `ukr` LIKE '%' || :searchQuery || '%' OR `eng` LIKE '%' || :searchQuery || '%' OR `swe` LIKE '%' || :searchQuery || '%')")
    fun observeWordsBySearchQueryInCategories(
        searchQuery: String,
        categoryNames: List<String>
    ): Flow<List<WordEntity>>

    @Query("SELECT * FROM wordentity WHERE categoryName IN (:categoryNames) AND (rus LIKE '%' || :searchQuery || '%' OR `ukr` LIKE '%' || :searchQuery || '%' OR `eng` LIKE '%' || :searchQuery || '%' OR `swe` LIKE '%' || :searchQuery || '%')")
    suspend fun getWordsBySearchQueryInCategories(
        searchQuery: String,
        categoryNames: List<String>
    ): List<WordEntity>

    @Query("SELECT * FROM wordentity WHERE categoryName = :catName")
    suspend fun getWordsByCat(catName: String): List<WordEntity>

    @Query("SELECT * FROM wordentity WHERE id = :wordId")
    suspend fun getWordById(wordId: Int): WordEntity?

    @Query("SELECT * FROM wordentity WHERE categoryName IN (:activeCatsNames) AND wordStatus = 1 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWordFromActiveCats(activeCatsNames: List<String>): WordEntity

    @Query("SELECT * FROM wordentity WHERE categoryName = :catName AND wordStatus = 1")
    fun observeActiveWordsByCat(catName: String): Flow<List<WordEntity>>

    @Query("SELECT * FROM wordentity WHERE categoryName = :catName")
    fun observeAllWordsByCat(catName: String): Flow<List<WordEntity>>

    fun observeWords(catName: String, searchQuery: String): Flow<List<WordEntity>> =
        if (catName.isBlank()) {
            observeWordsByQuery(searchQuery)
        } else {
            observeWordsByCatAndQuery(catName, searchQuery)
        }

    @Query("SELECT * FROM wordentity WHERE categoryName = :catName AND (rus LIKE '%' || :searchQuery || '%' OR `ukr` LIKE '%' || :searchQuery || '%' OR `eng` LIKE '%' || :searchQuery || '%' OR `swe` LIKE '%' || :searchQuery || '%') ORDER BY `rus` ASC") // todo order by
    fun observeWordsByCatAndQuery(catName: String, searchQuery: String): Flow<List<WordEntity>>

    @Query("SELECT * FROM wordentity WHERE (rus LIKE '%' || :searchQuery || '%' OR `ukr` LIKE '%' || :searchQuery || '%' OR `eng` LIKE '%' || :searchQuery || '%' OR `swe` LIKE '%' || :searchQuery || '%') ORDER BY `rus` ASC") // todo order by
    fun observeWordsByQuery(searchQuery: String): Flow<List<WordEntity>>

    @Transaction
    @Query("SELECT * FROM categoryentity")
    fun observeAllCategoriesWithWords(): Flow<List<CategoryWithWords>>

    @Query("SELECT * FROM categoryentity WHERE name = :name")
    suspend fun getCategoryByName(name: String): CategoryEntity

    @Query("SELECT name FROM categoryentity WHERE isCategoryActive = 1")
    suspend fun getALlActiveCatsNames(): List<String>

    @Query("SELECT name FROM categoryentity")
    suspend fun getAllCatNames(): List<String>

    @Query("SELECT isCategoryActive FROM categoryentity WHERE name = :catName")
    suspend fun isCategoryActive(catName: String): Boolean

    @Query("SELECT name FROM categoryentity WHERE isCategoryActive = 1")
    fun observeAllActiveCatsNames(): Flow<List<String>>

    @Query("SELECT * FROM wordentity WHERE rus = :rus AND `eng` = :eng AND categoryName = :categoryName LIMIT 1")
    fun observeWord(
        rus: String,
        eng: String,
        categoryName: String
    ): Flow<WordEntity> // todo ???pass chosen langs (all 4 with nulls) but not rus and eng???

    //===================STANDARD FUNCTIONS==============================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordEntity)

    @Update
    suspend fun updateWord(word: WordEntity)
}