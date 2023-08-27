package space.rodionov.porosenokpetr.feature_launcher.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class CheckVocabularyUseCase(
    private val repository: WordRepo,
    private val initialSetupUseCase: InitialSetupUseCase,
    private val parseRemoteVocabularyUseCase: ParseRemoteVocabularyUseCase
) {

    suspend operator fun invoke(): Boolean{
        return if (repository.getWordsQuantity() == 0) {
            try {
//                initialSetupUseCase.invoke()
                parseRemoteVocabularyUseCase.invoke()
            } catch (e: Exception) {
                throw e
            }
            true
        } else {
            true
        }
    }
}