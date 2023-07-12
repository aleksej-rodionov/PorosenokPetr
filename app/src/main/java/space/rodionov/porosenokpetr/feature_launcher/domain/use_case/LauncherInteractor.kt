package space.rodionov.porosenokpetr.feature_launcher.domain.use_case

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.core.data.local.entity.WordRaw
import space.rodionov.porosenokpetr.core.data.local.entity.swedishCategories
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.util.Language

class LauncherInteractor( //todo split to useCases
    private val repository: WordRepo,
    private val appScope: CoroutineScope,
    private val context: Context,
    private val setLearnedLanguageUseCase: SetLearnedLanguageUseCase,
    private val setAvailableNativeLanguagesUseCase: SetAvailableNativeLanguagesUseCase
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

            setLearnedLanguageUseCase.invoke(Language.Swedish)
            setAvailableNativeLanguagesUseCase(
                listOf(
                    Language.Russian,
                    Language.Ukrainian,
                    Language.English
                )
            )
            //todo set default native language

            Log.d("TAG_DB", "onCreate: finished")

            emit(true)

        } else {
            emit(false)
        }
    }

    private fun parseVocabulary(context: Context): List<WordRaw> {
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
}