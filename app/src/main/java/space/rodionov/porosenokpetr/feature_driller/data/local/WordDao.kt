package space.rodionov.porosenokpetr.feature_driller.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.feature_driller.data.local.entity.CategoryEntity
import space.rodionov.porosenokpetr.feature_driller.data.local.entity.CategoryWithWords
import space.rodionov.porosenokpetr.feature_driller.data.local.entity.WordEntity

@Dao
interface WordDao {

    @Query("SELECT * FROM wordentity WHERE categoryName IN (SELECT name FROM categoryentity WHERE isCategoryActive = 1) AND isWordActive = 1 ORDER BY RANDOM() LIMIT 10")
    suspend fun getTenWords(): List<WordEntity>

    @Query("SELECT * FROM wordentity WHERE nativ = :nativ AND `foreign` = :foreign AND categoryName = :categoryName LIMIT 1")
    suspend fun getWord(nativ: String, foreign: String, categoryName: String): WordEntity

    @Query("SELECT * FROM wordentity WHERE categoryName IN (:activeCatsNames) AND isWordActive = 1 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWordFromActiveCats(activeCatsNames: List<String>) : WordEntity

    @Query("SELECT * FROM categoryentity")
    fun observeAllCategories(): Flow<List<CategoryEntity>>

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