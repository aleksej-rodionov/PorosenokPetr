package space.rodionov.porosenokpetr.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import space.rodionov.porosenokpetr.core.data.local.entity.CategoryEntity
import space.rodionov.porosenokpetr.core.data.local.entity.ExampleConverters
import space.rodionov.porosenokpetr.core.data.local.entity.WordEntity

@Database(entities = [CategoryEntity::class, WordEntity::class], version = 1, exportSchema = false)
@TypeConverters(ExampleConverters::class)
abstract class WordDatabase : RoomDatabase() {

    abstract val dao: WordDao
}



