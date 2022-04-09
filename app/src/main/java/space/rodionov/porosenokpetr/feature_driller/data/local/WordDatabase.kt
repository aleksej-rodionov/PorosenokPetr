package space.rodionov.porosenokpetr.feature_driller.data.local

import android.app.Application
import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.feature_driller.data.local.entity.CategoryEntity
import space.rodionov.porosenokpetr.feature_driller.data.local.entity.WordEntity
import space.rodionov.porosenokpetr.feature_driller.di.ApplicationScope
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_DB_REFACTOR
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_NATIVE_LANG
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_PETR
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
                    Log.d(TAG_NATIVE_LANG, "onCreate: flavor = englishdriller")
                    val catNamesRus = app.resources.getStringArray(R.array.cat_name_rus).toList()
                    val catNamesUkr = app.resources.getStringArray(R.array.cat_name_ukr).toList()
                    catNamesRus.forEachIndexed { index, s ->
                        dao.insertCategory(CategoryEntity(
                            resourceName = s,
                            nameRus = s,
                            nameUkr = catNamesUkr[index]
                        ))
                    }

                    catNamesRus.forEachIndexed { index, s ->

                        val engResId = app.resources.getIdentifier(
                            "eng$index",
                            "array",
                            app.packageName
                        )
                        val engWords = app.resources.getStringArray(engResId).toList()
                        Log.d(TAG_PETR, "engwords size = ${engWords.size}: ")

                        val rusResId = app.resources.getIdentifier(
                            "rus$index",
                            "array",
                            app.packageName
                        )
                        val rusWords = app.resources.getStringArray(rusResId).toList()
                        Log.d(TAG_PETR, "ruswords size = ${rusWords.size}: ")

                        for (i in engWords.indices) {
                            dao.insertWord(WordEntity(engWords[i], rusWords[i], null, null, categoryName = s))  // todo add ukrainian later
                        }
                    }

                /*    val rus0 = app.resources.getStringArray(R.array.rus0).toList()
                    val rus1 = app.resources.getStringArray(R.array.rus1).toList()
                    val rus2 = app.resources.getStringArray(R.array.rus2).toList()
                    val rus3 = app.resources.getStringArray(R.array.rus3).toList()
                    val rus4 = app.resources.getStringArray(R.array.rus4).toList()
                    val rus5 = app.resources.getStringArray(R.array.rus5).toList()
                    val foreign0 = app.resources.getStringArray(R.array.eng0).toList()
                    val foreign1 = app.resources.getStringArray(R.array.eng1).toList()
                    val foreign2 = app.resources.getStringArray(R.array.eng2).toList()
                    val foreign3 = app.resources.getStringArray(R.array.eng3).toList()
                    val foreign4 = app.resources.getStringArray(R.array.eng4).toList()
                    val foreign5 = app.resources.getStringArray(R.array.eng5).toList()

                    for (i in foreign0.indices) {
                        dao.insertWord(
                            WordEntity(
                                rus0[i],
                                foreign0[i],
                                app.resources.getString(R.string.top_800_words)
                            )
                        )
                    }
                    for (i in foreign1.indices) {
                        dao.insertWord(
                            WordEntity(
                                rus1[i],
                                foreign1[i],
                                app.resources.getString(R.string.phrases)
                            )
                        )
                    }
                    for (i in foreign2.indices) {
                        dao.insertWord(
                            WordEntity(
                                rus2[i],
                                foreign2[i],
                                app.resources.getString(R.string.phrasal_verbs)
                            )
                        )
                    }
                    for (i in foreign3.indices) {
                        dao.insertWord(
                            WordEntity(
                                rus3[i],
                                foreign3[i],
                                app.resources.getString(R.string.usual_verbs)
                            )
                        )
                    }
                    for (i in foreign4.indices) {
                        dao.insertWord(
                            WordEntity(
                                rus4[i],
                                foreign4[i],
                                app.resources.getString(R.string.usual_words)
                            )
                        )
                    }
                    for (i in foreign5.indices) {
                        dao.insertWord(
                            WordEntity(
                                rus5[i],
                                foreign5[i],
                                app.resources.getString(R.string.top_200_verbs)
                            )
                        )
                    }*/
                }

                if (BuildConfig.FLAVOR == "swedishdriller") {
                    Log.d(TAG_NATIVE_LANG, "onCreate: flavor = swedishdriller")

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
                        Log.d(TAG_DB_REFACTOR, "insertCat: $cat")
                        dao.insertCategory(cat)
                    }

                    catNamesRus.forEachIndexed { index, s ->
                        val rusResId = app.resources.getIdentifier("rus$index", "array", app.packageName)
                        val rusWords = app.resources.getStringArray(rusResId).toList()
                        Log.d(TAG_PETR, "ruswords size = ${rusWords.size}: ")

                        val ukrResId = app.resources.getIdentifier("ukr$index", "array", app.packageName)
                        val ukrWords = app.resources.getStringArray(ukrResId).toList()
                        Log.d(TAG_PETR, "ruswords size = ${ukrWords.size}: ")

                        val engResId = app.resources.getIdentifier("eng$index", "array", app.packageName)
                        val engWords = app.resources.getStringArray(engResId).toList()
                        Log.d(TAG_PETR, "engwords size = ${engWords.size}: ")

                        val sweResId = app.resources.getIdentifier("swe$index", "array", app.packageName)
                        val sweWords = app.resources.getStringArray(sweResId).toList()
                        Log.d(TAG_PETR, "ruswords size = ${sweWords.size}: ")

                        for (i in engWords.indices) {
                            dao.insertWord(WordEntity(engWords[i], rusWords[i], ukrWords[i], sweWords[i], s))
                        }
                    }

                }
            }
        }
    }
}





