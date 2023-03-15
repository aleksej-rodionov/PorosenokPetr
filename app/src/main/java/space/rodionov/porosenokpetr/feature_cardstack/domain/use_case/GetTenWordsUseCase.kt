package space.rodionov.porosenokpetr.feature_cardstack.domain.use_case

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.util.Resource
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class GetTenWordsUseCase(
    private val repo: WordRepo
) {

    operator fun invoke(): Flow<Resource<List<Word>>> {
        return repo.getTenWords()
    }
}