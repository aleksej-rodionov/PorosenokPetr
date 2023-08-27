package space.rodionov.porosenokpetr.feature_launcher.domain.use_case

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import space.rodionov.porosenokpetr.core.data.local.entity.WordRaw
import space.rodionov.porosenokpetr.core.domain.common.UseCaseException
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateNativeLanguageUseCase

class InitialSetupUseCase(
    private val repository: WordRepo,
    private val context: Context,
    private val setLearnedLanguageUseCase: SetLearnedLanguageUseCase,
    private val setAvailableNativeLanguagesUseCase: SetAvailableNativeLanguagesUseCase,
    private val updateNativeLanguageUseCase: UpdateNativeLanguageUseCase
) {

    suspend operator fun invoke(): Boolean {
        Categories.swedishCategories.forEach {
            repository.insertCategory(it)
        }
        val rawWordsFromJson = parseVocabulary(context)
        rawWordsFromJson.forEach {
            repository.insertWord(Word(it.catName, it.rus, it.eng, it.ukr, it.swe, it.examples))
        }
        setLearnedLanguageUseCase.invoke(Language.Swedish)
        setAvailableNativeLanguagesUseCase(
            listOf(
                Language.Russian,
                Language.Ukrainian,
                Language.English
            )
        )
        updateNativeLanguageUseCase.invoke(Language.Russian)
        return true
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
            throw UseCaseException("Unable to parse vocabulary: ${e.message}")
        }

        val wordListTYpe = object : TypeToken<List<WordRaw>>() {}.type
        val wordRawList: List<WordRaw> = Gson().fromJson(vocabularyJson, wordListTYpe)
        return wordRawList
    }
}