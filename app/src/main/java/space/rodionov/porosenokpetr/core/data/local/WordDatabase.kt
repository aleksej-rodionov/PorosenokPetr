package space.rodionov.porosenokpetr.core.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import space.rodionov.porosenokpetr.core.data.local.entity.CategoryEntity
import space.rodionov.porosenokpetr.core.data.local.entity.WordEntity
import space.rodionov.porosenokpetr.core.data.local.entity.WordRaw

@Database(entities = [CategoryEntity::class, WordEntity::class], version = 1)
abstract class WordDatabase : RoomDatabase() {

    abstract val dao: WordDao
}



