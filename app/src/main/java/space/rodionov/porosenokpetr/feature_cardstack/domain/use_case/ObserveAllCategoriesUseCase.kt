package space.rodionov.porosenokpetr.feature_cardstack.domain.use_case

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class ObserveAllCategoriesUseCase(
    private val repo: WordRepo
) {

    operator fun invoke(): Flow<List<Category>> = repo.observeAllCategories()
}