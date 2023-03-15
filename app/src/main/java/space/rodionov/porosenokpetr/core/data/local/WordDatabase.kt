package space.rodionov.porosenokpetr.core.data.local

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.data.local.entity.CategoryEntity
import space.rodionov.porosenokpetr.core.data.local.entity.WordEntity
import space.rodionov.porosenokpetr.core.data.local.entity.WordRaw
import space.rodionov.porosenokpetr.core.data.local.mapper.toWordEntity
import space.rodionov.porosenokpetr.core.di.ApplicationScope
import space.rodionov.porosenokpetr.core.util.Constants.TAG_DB_REFACTOR
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [CategoryEntity::class, WordEntity::class], version = 1)
abstract class WordDatabase : RoomDatabase() {

    abstract val dao: WordDao

    class Callback @Inject constructor(
        private val app: Application,
        private val database: Provider<WordDatabase>,
        @ApplicationScope private val appScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().dao

            appScope.launch {

                if (BuildConfig.FLAVOR == "swedishdriller") {
                    Log.d("TAG_DB", "onCreate: started")

                    val rawWordsFromJson = parseVocabulary(app)
                    //todo then try to make this more economical
                    val wordEntities = rawWordsFromJson.map {
                        it.toWordEntity()
                    }

                    wordEntities.forEach {
                        dao.insertWord(it)
                    }
                    Log.d("TAG_DB", "onCreate: finished")
                }
            }
        }
    }
}

fun parseVocabulary(context: Context): List<WordRaw> {
    var vocabularyJson = ""
    try {
        vocabularyJson = context.assets.open("vocabulary/vocabulary_swedish.json")
            .bufferedReader()
            .use {
                it.readText()
            }
    } catch (e: Exception) {
        Log.d("TAG_DB", "parseVocabulary: exception = ${e.message}")
    }

    val wordListTYpe = object : TypeToken<List<WordRaw>>() {}.type
    val wordRawList: List<WordRaw> = Gson().fromJson(vocabularyJson, wordListTYpe)
    return wordRawList
}



