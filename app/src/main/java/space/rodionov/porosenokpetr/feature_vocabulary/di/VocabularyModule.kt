package space.rodionov.porosenokpetr.feature_vocabulary.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.domain.use_case.MakeCategoryActiveUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SpeakWordUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateLearnedPercentInCategoryUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateWordUseCase
import space.rodionov.porosenokpetr.core.util.ForeignSpeaker
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.ObserveAllCategoriesUseCase
import space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case.ObserveWordsBySearchQueryInCategories
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyViewModel

@Module
class VocabularyModule {

    @Provides
    @VocabularyScope
    fun provideObserveAllCategoriesUseCase(repo: WordRepo) = ObserveAllCategoriesUseCase(repo)

    @Provides
    @VocabularyScope
    fun provideObserveWordsBySearchQueryInCategories(repo: WordRepo) =
        ObserveWordsBySearchQueryInCategories(repo)

    @Provides
    @VocabularyScope
    fun provideMakeCategoryActiveUseCase(repo: WordRepo) = MakeCategoryActiveUseCase(repo)

    @Provides
    @VocabularyScope
    fun provideSpeakWordUseCase(foreignSpeaker: ForeignSpeaker) = SpeakWordUseCase(foreignSpeaker)

    @Provides
    @VocabularyScope
    fun provideUpdateWordUseCase(repo: WordRepo) = UpdateWordUseCase(repo)

    @Provides
    @VocabularyScope
    fun provideUpdateLearnedPercentInCategoryUseCase(repo: WordRepo) =
        UpdateLearnedPercentInCategoryUseCase(repo)

    @Provides
    @VocabularyScope
    fun provideVocabularyViewModel(
        observeAllCategoriesUseCase: ObserveAllCategoriesUseCase,
        observeWordsBySearchQueryInCategories: ObserveWordsBySearchQueryInCategories,
        makeCategoryActiveUseCase: MakeCategoryActiveUseCase,
        speakWordUseCase: SpeakWordUseCase,
        updateWordUseCase: UpdateWordUseCase,
        updateLearnedPercentInCategoryUseCase: UpdateLearnedPercentInCategoryUseCase
    ) = VocabularyViewModel(
        observeAllCategoriesUseCase,
        observeWordsBySearchQueryInCategories,
        makeCategoryActiveUseCase,
        speakWordUseCase,
        updateWordUseCase,
        updateLearnedPercentInCategoryUseCase
    )
}