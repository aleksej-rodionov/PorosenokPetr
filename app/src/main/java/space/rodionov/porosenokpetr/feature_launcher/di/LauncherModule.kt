package space.rodionov.porosenokpetr.feature_launcher.di

import android.app.Application
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.LauncherInteractor
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.SetAvailableNativeLanguagesUseCase
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.SetLearnedLanguageUseCase
import space.rodionov.porosenokpetr.feature_launcher.presentation.LauncherViewModel
import space.rodionov.porosenokpetr.main.di.AppCoroutineScopeQualifier

@Module
class LauncherModule {

    @Provides
    @LauncherScope
    fun provideSetLearnedLanguageUseCase(keyValueStorage: KeyValueStorage) =
        SetLearnedLanguageUseCase(keyValueStorage)

    @Provides
    @LauncherScope
    fun provideSetAvailableNativeLanguagesUseCase(keyValueStorage: KeyValueStorage) =
        SetAvailableNativeLanguagesUseCase(keyValueStorage)

    @Provides
    @LauncherScope
    fun provideLauncherInteractor(
        repo: WordRepo,
        @AppCoroutineScopeQualifier appScope: CoroutineScope,
        app: Application,
        setLearnedLanguageUseCase: SetLearnedLanguageUseCase,
        setAvailableNativeLanguagesUseCase: SetAvailableNativeLanguagesUseCase
    ) = LauncherInteractor(
        repo,
        appScope,
        app,
        setLearnedLanguageUseCase,
        setAvailableNativeLanguagesUseCase
    )

    @Provides
    @LauncherScope
    fun provideLauncherViewModel(
        launcherInteractor: LauncherInteractor
    ) = LauncherViewModel(launcherInteractor)
}