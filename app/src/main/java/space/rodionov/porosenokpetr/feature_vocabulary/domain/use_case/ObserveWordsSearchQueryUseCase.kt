package space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class ObserveWordsSearchQueryUseCase(
    private val repo: WordRepo
) {

    operator fun invoke(catName: String, searchQuery: String): Flow<List<Word>> =
        repo.wordsBySearchQuery(catName, searchQuery)
}