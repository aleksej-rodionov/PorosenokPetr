package space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case

import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class GetWordsBySearchQueryInCategories(
    private val repo: WordRepo
) {

    operator suspend fun invoke(
        query: String,
        categories: List<Category>
    ): List<Word> {
        return repo.getWordsBySearchQueryInCategories(
            query,
            categories
        )
    }
}