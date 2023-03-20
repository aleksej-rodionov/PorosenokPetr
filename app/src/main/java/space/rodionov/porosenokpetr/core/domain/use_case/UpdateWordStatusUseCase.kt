package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class UpdateWordStatusUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(word: Word, status: Int) {
        repo.updateWordStatus(word, status)
    }
}