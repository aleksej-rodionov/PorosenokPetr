package space.rodionov.porosenokpetr.feature_vocabulary.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.domain.use_case.MakeCategoryActiveUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SpeakWordUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateLearnedPercentInCategoryUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateWordStatusUseCase
import space.rodionov.porosenokpetr.core.util.SwedishSpeaker
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.ObserveAllCategoriesUseCase
import space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case.*
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyViewModel
import javax.inject.Singleton

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
    fun provideSpeakWordUseCase(swedishSpeaker: SwedishSpeaker) = SpeakWordUseCase(swedishSpeaker)

    @Provides
    @VocabularyScope
    fun provideUpdateWordStatusUseCase(repo: WordRepo) = UpdateWordStatusUseCase(repo)

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
        updateWordStatusUseCase: UpdateWordStatusUseCase,
        updateLearnedPercentInCategoryUseCase: UpdateLearnedPercentInCategoryUseCase
    ) = VocabularyViewModel(
        observeAllCategoriesUseCase,
        observeWordsBySearchQueryInCategories,
        makeCategoryActiveUseCase,
        speakWordUseCase,
        updateWordStatusUseCase,
        updateLearnedPercentInCategoryUseCase
    )
}