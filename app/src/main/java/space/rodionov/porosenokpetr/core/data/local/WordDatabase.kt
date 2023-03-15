package space.rodionov.porosenokpetr.core.data.local

import android.app.Application
import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.data.local.entity.CategoryEntity
import space.rodionov.porosenokpetr.core.data.local.entity.WordEntity
import space.rodionov.porosenokpetr.core.di.ApplicationScope
import space.rodionov.porosenokpetr.core.util.Constants.TAG_DB_REFACTOR
import space.rodionov.porosenokpetr.core.util.Constants.TAG_NATIVE_LANG
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
                if (BuildConfig.FLAVOR == "englishdriller") {
                    Log.d(TAG_DB_REFACTOR, "onCreate: flavor = englishdriller")
                    val catNamesRus = app.resources.getStringArray(R.array.cat_name_rus).toList()
                    val catNamesUkr = app.resources.getStringArray(R.array.cat_name_ukr).toList()
                    catNamesRus.forEachIndexed { index, s ->
                        dao.insertCategory(
                            CategoryEntity(
                            resourceName = s,
                            nameRus = s,
                            nameUkr = catNamesUkr[index]
                        )
                        )
                    }

//                    val words = mutableListOf<WordEntity>()

                    catNamesRus.forEachIndexed { index, s ->

                        val rusResId = app.resources.getIdentifier("rus$index", "array", app.packageName)
                        val rusWords = app.resources.getStringArray(rusResId).toList()
                        Log.d(TAG_DB_REFACTOR, "ruswords size = ${rusWords.size}: ")

                        val engResId = app.resources.getIdentifier("eng$index", "array", app.packageName)
                        val engWords = app.resources.getStringArray(engResId).toList()
                        Log.d(TAG_DB_REFACTOR, "engwords size = ${engWords.size}: ")

                        for (i in engWords.indices) {
                            dao.insertWord(WordEntity(rusWords[i], null, engWords[i], null, categoryName = s))  // todo add ukrainian later

//                            if (i == 0) words.add(WordEntity(rusWords[i], null, engWords[i], null, categoryName = s))
                        }

//                        val wordsJson = Gson().toJson(words)

//                        Log.d("WORDS_JSON", wordsJson)
                    }
                }

                if (BuildConfig.FLAVOR == "swedishdriller") {
                    Log.d(TAG_DB_REFACTOR, "onCreate: flavor = swedishdriller")

                    val catNamesRus = app.resources.getStringArray(R.array.cat_name_rus).toList()
                    val catNamesUkr = app.resources.getStringArray(R.array.cat_name_ukr).toList()
                    val catNamesEng = app.resources.getStringArray(R.array.cat_name_eng).toList()
                    catNamesRus.forEachIndexed { index, s ->
                        val cat = CategoryEntity(
                            resourceName = s,
                            nameRus = s,
                            nameUkr = catNamesUkr[index],
                            nameEng = catNamesEng[index]
                        )
                        dao.insertCategory(cat)
                    }

                    catNamesRus.forEachIndexed { index, s ->
                        Log.d(TAG_DB_REFACTOR, "onCreate: cat = $s")

                        val rusResId = app.resources.getIdentifier("rus${index+1}", "array", app.packageName)
                        val rusWords = app.resources.getStringArray(rusResId).toList()
                        Log.d(TAG_NATIVE_LANG, "ruswords size = ${rusWords.size}: ")

                        val ukrResId = app.resources.getIdentifier("ukr${index+1}", "array", app.packageName)
                        val ukrWords = app.resources.getStringArray(ukrResId).toList()
                        Log.d(TAG_DB_REFACTOR, "ruswords size = ${ukrWords.size}: ")

                        val engResId = app.resources.getIdentifier("eng${index+1}", "array", app.packageName)
                        val engWords = app.resources.getStringArray(engResId).toList()
                        Log.d(TAG_DB_REFACTOR, "engwords size = ${engWords.size}: ")

                        val sweResId = app.resources.getIdentifier("foreign${index+1}", "array", app.packageName)
                        val sweWords = app.resources.getStringArray(sweResId).toList()
                        Log.d(TAG_DB_REFACTOR, "ruswords size = ${sweWords.size}: ")

                        for (i in engWords.indices) {
                            dao.insertWord(WordEntity(rusWords[i], ukrWords[i], engWords[i], sweWords[i], categoryName = s))
                        }
                    }

                }
            }
        }
    }
}





