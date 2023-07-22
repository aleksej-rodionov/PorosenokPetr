package space.rodionov.porosenokpetr.feature_launcher.di

import android.app.Application
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateInterfaceLanguageUseCase
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.CheckInterfaceLocaleConfigUseCase
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.CheckVocabularyUseCase
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.InitialSetupUseCase
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.SetAvailableNativeLanguagesUseCase
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.SetLearnedLanguageUseCase
import space.rodionov.porosenokpetr.feature_launcher.presentation.LauncherViewModel
import space.rodionov.porosenokpetr.feature_settings.di.SettingsScope
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateNativeLanguageUseCase
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
    fun provideUpdateNativeLanguageUseCase(keyValueStorage: KeyValueStorage) =
        UpdateNativeLanguageUseCase(keyValueStorage)

    @Provides
    @LauncherScope
    fun provideInitialSetupUseCase(
        repo: WordRepo,
        app: Application,
        setLearnedLanguageUseCase: SetLearnedLanguageUseCase,
        setAvailableNativeLanguagesUseCase: SetAvailableNativeLanguagesUseCase,
        updateNativeLanguageUseCase: UpdateNativeLanguageUseCase
    ) = InitialSetupUseCase(
        repo,
        app,
        setLearnedLanguageUseCase,
        setAvailableNativeLanguagesUseCase,
        updateNativeLanguageUseCase
    )

    @Provides
    @LauncherScope
    fun provideCheckVocabularyUseCase(
        repo: WordRepo,
        initialSetupUseCase: InitialSetupUseCase
    ) = CheckVocabularyUseCase(repo, initialSetupUseCase)

    @Provides
    @LauncherScope
    fun provideUpdateInterfaceLanguageUseCase(keyValueStorage: KeyValueStorage) =
        UpdateInterfaceLanguageUseCase(keyValueStorage)

    @Provides
    @LauncherScope
    fun provideCheckInterfaceLocaleConfigUseCase(
        app: Application,
        @AppCoroutineScopeQualifier appCoroutineScope: CoroutineScope,
        updateInterfaceLanguageUseCase: UpdateInterfaceLanguageUseCase
    ) = CheckInterfaceLocaleConfigUseCase(app, appCoroutineScope, updateInterfaceLanguageUseCase)

    @Provides
    @LauncherScope
    fun provideLauncherViewModel(
        checkVocabularyUseCase: CheckVocabularyUseCase,
        checkInterfaceLocaleConfigUseCase: CheckInterfaceLocaleConfigUseCase
    ) = LauncherViewModel(
        checkVocabularyUseCase,
        checkInterfaceLocaleConfigUseCase
    )
}