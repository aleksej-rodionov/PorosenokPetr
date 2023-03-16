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

//    class Callback @Inject constructor(
//        private val app: Application,
//        private val database: Provider<WordDatabase>,
//        @ApplicationScope private val appScope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//
//            val dao = database.get().dao
//
//            appScope.launch {
////todo move DB populating to separate UseCase
//                if (BuildConfig.FLAVOR == "swedishdriller") {
//
//                    Log.d("TAG_DB", "onCreate: started")
//                    swedishCategories.forEach {
//                        dao.insertCategory(it.toCategoryEntity())
//                    }
//
//                    val rawWordsFromJson = parseVocabulary(app)
//                    rawWordsFromJson.forEach {
//                        dao.insertWord(WordEntity(it.rus, it.ukr, it.eng, it.swe, it.catName))
//                    }
//                    Log.d("TAG_DB", "onCreate: finished")
//                }
//            }
//        }
//    }
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



