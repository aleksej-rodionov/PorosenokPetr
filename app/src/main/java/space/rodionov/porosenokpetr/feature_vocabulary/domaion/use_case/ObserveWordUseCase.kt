package space.rodionov.porosenokpetr.feature_vocabulary.domaion.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

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