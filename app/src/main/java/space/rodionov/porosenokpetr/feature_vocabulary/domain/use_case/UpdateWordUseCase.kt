package space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case

import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class UpdateWordUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(word: Word, newWord: Word) = repo.updateWord(word, newWord)
}