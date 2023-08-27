package space.rodionov.porosenokpetr.feature_launcher.domain.use_case

import space.rodionov.porosenokpetr.core.data.local.entity.WordRaw
import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.RemoteVocabularyRepository
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.Categories.englishCategories
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateNativeLanguageUseCase

class ParseRemoteVocabularyUseCase(
    private val remoteRepository: RemoteVocabularyRepository,
    private val localRepository: WordRepo,
    private val setLearnedLanguageUseCase: SetLearnedLanguageUseCase,
    private val setAvailableNativeLanguagesUseCase: SetAvailableNativeLanguagesUseCase,
    private val updateNativeLanguageUseCase: UpdateNativeLanguageUseCase
) {

    suspend operator fun invoke(): Boolean {
        val words = remoteRepository.fetchAllWords()
        words.forEach {
            localRepository.insertWord(
                Word(
                    categoryName = it.catName,
                    rus = it.rus,
                    eng = it.eng,
                    examples = it.examples
                )
            )
        }

        val categories = getPossibleCategories(words)
        categories.forEach {
            localRepository.insertCategory(it)
        }

        setLearnedLanguageUseCase.invoke(Language.English)
        setAvailableNativeLanguagesUseCase(
            listOf(
                Language.Russian
            )
        )
        updateNativeLanguageUseCase.invoke(Language.Russian)
        return true
    }

    private fun getPossibleCategories(words: List<WordRaw>): List<Category> {
        return words.map {
            it.catName
        }.distinct().map { catName ->
            englishCategories.find { it.name == catName }!! //todo shit, I need to parse categories right from wordList
        }
    }
}