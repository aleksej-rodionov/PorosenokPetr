package space.rodionov.porosenokpetr.feature_cardstack.domain.use_case

import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class UpdateWordIsActiveUseCase(
    private val repo: WordRepo
) {

     suspend operator fun invoke(word: Word, isActive: Boolean) {
         repo.updateWordIsActive(word, isActive)
     }
}