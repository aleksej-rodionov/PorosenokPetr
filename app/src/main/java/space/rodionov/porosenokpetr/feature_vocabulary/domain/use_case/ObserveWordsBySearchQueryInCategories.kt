package space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class ObserveWordsBySearchQueryInCategories(
    private val repo: WordRepo
) {

    operator fun invoke(
        query: String,
        categories: List<Category>
    ): Flow<List<Word>> {
        return repo.observeWordsBySearchQueryInCategories(
            query,
            categories
        )
    }
}