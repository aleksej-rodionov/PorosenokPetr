package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class UpdateWordIsActiveUseCase(
    private val repo: WordRepo
) {

     suspend operator fun invoke(word: Word, isActive: Boolean) {
         repo.updateWordIsActive(word, isActive)
     }
}