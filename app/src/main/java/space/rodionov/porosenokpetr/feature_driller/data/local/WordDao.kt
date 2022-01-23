package space.rodionov.porosenokpetr.feature_driller.data.local

import androidx.room.*
import space.rodionov.porosenokpetr.feature_driller.data.local.entity.CategoryEntity
import space.rodionov.porosenokpetr.feature_driller.data.local.entity.WordEntity

@Dao
interface WordDao {

    @Query("SELECT * FROM wordentity WHERE categoryName IN (SELECT name FROM categoryentity WHERE isCategoryActive = 1) AND isWordActive = 1 ORDER BY RANDOM() LIMIT 10")
    suspend fun getTenWords(): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordEntity)

    @Update
    suspend fun updateWord(word: WordEntity)
}