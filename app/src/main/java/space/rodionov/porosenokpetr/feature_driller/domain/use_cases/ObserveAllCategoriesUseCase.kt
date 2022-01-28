package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class ObserveAllCategoriesUseCase(
    private val repo: WordRepo
) {

    operator fun invoke(): Flow<List<Category>> = repo.observeAllCategories()
}