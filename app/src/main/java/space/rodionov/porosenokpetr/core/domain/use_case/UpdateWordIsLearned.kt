package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class UpdateWordIsLearned(
    private val repo: WordRepo
) {

    operator suspend fun invoke(
        word: Word,
        isLearned: Boolean
    ) {
        repo.updateWordIsLearned(word, isLearned)
    }
}