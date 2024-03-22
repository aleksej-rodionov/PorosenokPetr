package space.rodionov.porosenokpetr.feature_launcher.domain.use_case

import space.rodionov.porosenokpetr.core.data.local.entity.WordRaw
import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.RemoteVocabularyRepository
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.Categories.swedishCategories
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
            localRepository.insertWord(Word(it.catName, it.rus, it.eng, it.ukr, it.swe, it.examples))
        }

        val categories = getPossibleCategories(words)
        categories.forEach {
            localRepository.insertCategory(it)
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

    private fun getPossibleCategories(words: List<WordRaw>): List<Category> {
        return words.map {
            it.catName
        }.distinct().mapNotNull { catName ->
            swedishCategories.find {
                it.name == catName //todo shit, I need to parse categories right from wordList
            }
        }
    }
}