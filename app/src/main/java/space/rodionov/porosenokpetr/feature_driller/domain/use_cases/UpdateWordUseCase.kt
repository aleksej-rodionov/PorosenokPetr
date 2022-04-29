package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class UpdateWordUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(word: Word, newWord: Word) = repo.updateWord(word, newWord)
}