package space.rodionov.porosenokpetr.feature_driller.data.local

import android.app.Application
import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.feature_driller.data.local.entity.CategoryEntity
import space.rodionov.porosenokpetr.feature_driller.data.local.entity.WordEntity
import space.rodionov.porosenokpetr.feature_driller.di.ApplicationScope
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
                val categories = app.resources.getStringArray(R.array.categories_ru).toList()
                categories.forEach {
                    dao.insertCategory(CategoryEntity(it))
                }

                categories.forEachIndexed { index, s ->

    /*                val sweResId = app.resources.getIdentifier(
                        "eng$index",
                        "array",
                        app.packageName
                    )
                    val sweWords = app.resources.getStringArray(sweResId).toList()

                    val ukrResId = app.resources.getIdentifier(
                        "rus$index",
                        "array",
                        app.packageName
                    )
                    val ukrWords = app.resources.getStringArray(ukrResId).toList()*/

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

                    for (i in engWords.indices) { dao.insertWord(WordEntity(rusWords[i], engWords[i], s)) }
                }

                val rus0 = app.resources.getStringArray(R.array.rus0).toList()
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

                for (i in foreign0.indices) { dao.insertWord(WordEntity(rus0[i], foreign0[i], app.resources.getString(R.string.top_800_words))) }
                for (i in foreign1.indices) { dao.insertWord(WordEntity(rus1[i], foreign1[i], app.resources.getString(R.string.phrases))) }
                for (i in foreign2.indices) { dao.insertWord(WordEntity(rus2[i], foreign2[i], app.resources.getString(R.string.phrasal_verbs))) }
                for (i in foreign3.indices) { dao.insertWord(WordEntity(rus3[i], foreign3[i], app.resources.getString(R.string.usual_verbs))) }
                for (i in foreign4.indices) { dao.insertWord(WordEntity(rus4[i], foreign4[i], app.resources.getString(R.string.usual_words))) }
                for (i in foreign5.indices) { dao.insertWord(WordEntity(rus5[i], foreign5[i], app.resources.getString(R.string.top_200_verbs))) }
            }
        }
    }
}





