package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class ObserveWordUseCase(
    private val repo: WordRepo
) {

    operator fun invoke(rus: String?, eng: String?, categoryName: String?/*word: Word?*/): Flow<Word?> {
        if (rus==null || eng==null || categoryName==null/*word == null*/) {
            return flow {  }
        }
        return repo.observeWord(rus, eng, categoryName)
    }
}