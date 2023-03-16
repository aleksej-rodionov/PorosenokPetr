package space.rodionov.porosenokpetr.feature_splash.domain.use_case

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.core.data.local.entity.swedishCategories
import space.rodionov.porosenokpetr.core.data.local.parseVocabulary
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class SplashInteractor(
    private val repository: WordRepo,
    private val appScope: CoroutineScope,
    private val context: Context
) {

    suspend fun getWordQuantity() = repository.getWordsQuantity()

    suspend fun populateDatabase(): Flow<Boolean> = flow {

        emit(false)

        if (BuildConfig.FLAVOR == "swedishdriller") {

            Log.d("TAG_DB", "onCreate: started")
            swedishCategories.forEach {
                repository.insertCategory(it)
            }

            val rawWordsFromJson = parseVocabulary(context)
            rawWordsFromJson.forEach {
                repository.insertWord(Word(it.rus, it.ukr, it.eng, it.swe, it.catName))
            }
            Log.d("TAG_DB", "onCreate: finished")

            emit(true)

        } else {
            emit(false)
        }
    }
}