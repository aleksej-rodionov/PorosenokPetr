package space.rodionov.porosenokpetr.feature_vocabulary.domaion.use_case

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.domain.model.CatWithWords
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class ObserveAllCatsWithWordsUseCase(
    private val repo: WordRepo
) {

    operator fun invoke(): Flow<List<CatWithWords>> = repo.observeAllCategoriesWithWords()
}