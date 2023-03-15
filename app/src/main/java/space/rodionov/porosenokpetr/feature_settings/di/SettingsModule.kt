package space.rodionov.porosenokpetr.feature_settings.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.*
import javax.inject.Singleton

@Module
class SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsUseCases(
        preferences: Preferences
    ): SettingsUseCases {
        return SettingsUseCases(
            updateLearnedLangUseCase = UpdateLearnedLangUseCase(preferences),
            updateNativeLangUseCase = UpdateNativeLangUseCase(preferences),
            saveTranslationDirectionUseCase = SaveTranslationDirectionUseCase(preferences),
            setFollowSystemModeUseCase = SetFollowSystemModeUseCase(preferences),
        )
    }
}