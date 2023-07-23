package space.rodionov.porosenokpetr.feature_cardstack.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.domain.use_case.CollectModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectNativeLanguageUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectTranslationDirectionUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.GetLearnedLanguageUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SpeakWordUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateLearnedPercentInCategoryUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateWordUseCase
import space.rodionov.porosenokpetr.core.util.SwedishSpeaker
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.GetTenWordsUseCase
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardStackViewModel

@Module
class CardStackModule {

    @Provides
    @CardStackScope
    fun provideGetTenWordsUseCase(repo: WordRepo) = GetTenWordsUseCase(repo)

    @Provides
    @CardStackScope
    fun provideCollectModeUseCase(keyValueStorage: KeyValueStorage) =
        CollectModeUseCase(keyValueStorage)

    @Provides
    @CardStackScope
    fun provideCollectNativeLanguageUseCase(keyValueStorage: KeyValueStorage) =
        CollectNativeLanguageUseCase(keyValueStorage)

    @Provides
    @CardStackScope
    fun provideCollectTranslationDirectionUseCase(keyValueStorage: KeyValueStorage) =
        CollectTranslationDirectionUseCase(keyValueStorage)

    @Provides
    @CardStackScope
    fun provideGetLearnedLanguageUseCase() = GetLearnedLanguageUseCase()

    @Provides
    @CardStackScope
    fun provideUpdateWordUseCase(repo: WordRepo) = UpdateWordUseCase(repo)

    @Provides
    @CardStackScope
    fun provideUpdateLearnedPercentInCategoryUseCase(repo: WordRepo) =
        UpdateLearnedPercentInCategoryUseCase(repo)

    @Provides
    @CardStackScope
    fun provideSpeakWordUseCase(swedishSpeaker: SwedishSpeaker) = SpeakWordUseCase(swedishSpeaker)

    @Provides
    @CardStackScope
    fun provideCardStackViewModel(
        getTenWordsUseCase: GetTenWordsUseCase,
        collectModeUseCase: CollectModeUseCase,
        collectNativeLanguageUseCase: CollectNativeLanguageUseCase,
        collectTranslationDirectionUseCase: CollectTranslationDirectionUseCase,
        getLearnedLanguageUseCase: GetLearnedLanguageUseCase,
        updateWordUseCase: UpdateWordUseCase,
        updateLearnedPercentInCategoryUseCase: UpdateLearnedPercentInCategoryUseCase,
        speakWordUseCase: SpeakWordUseCase
    ) = CardStackViewModel(
        getTenWordsUseCase,
        collectModeUseCase,
        collectNativeLanguageUseCase,
        collectTranslationDirectionUseCase,
        getLearnedLanguageUseCase,
        updateWordUseCase,
        updateLearnedPercentInCategoryUseCase,
        speakWordUseCase
    )
}