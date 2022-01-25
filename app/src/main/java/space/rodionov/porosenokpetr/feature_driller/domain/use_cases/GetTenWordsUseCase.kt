package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.Resource
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class GetTenWordsUseCase(
    private val repo: WordRepo
) {

    operator fun invoke(): Flow<Resource<List<Word>>> {
        return repo.getTenWords()
    }
}