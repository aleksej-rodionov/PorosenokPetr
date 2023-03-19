package space.rodionov.porosenokpetr.feature_cardstack.domain.use_case

import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class GetAllCategoriesUseCase(
    private val repo: WordRepo
) {

    operator suspend fun invoke(): List<Category> = repo.getAllCategories()
}