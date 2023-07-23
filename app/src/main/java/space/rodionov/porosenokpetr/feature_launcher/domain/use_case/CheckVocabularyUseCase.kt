package space.rodionov.porosenokpetr.feature_launcher.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class CheckVocabularyUseCase(
    private val repository: WordRepo,
    private val initialSetupUseCase: InitialSetupUseCase
) {

    suspend operator fun invoke(): Boolean{
        return if (repository.getWordsQuantity() == 0) {
            try {
                initialSetupUseCase.invoke()
            } catch (e: Exception) {
                throw e
            }
            true
        } else {
            true
        }
    }
}