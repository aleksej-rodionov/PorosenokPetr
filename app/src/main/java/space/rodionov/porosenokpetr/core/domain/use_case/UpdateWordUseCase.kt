package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class UpdateWordUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(word: Word) {
        repo.updateWord(word)
    }
}