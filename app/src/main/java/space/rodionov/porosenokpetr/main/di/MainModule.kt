package space.rodionov.porosenokpetr.main.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveFollowSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SetModeUseCase
import javax.inject.Singleton

@Module
class MainModule {

//    @MainScope
    @Provides
    @Singleton
    fun provideObserveModeUseCase(preferences: Preferences) = ObserveModeUseCase(preferences)

//    @MainScope
    @Provides
    @Singleton
    fun provideSetModeUseCase(preferences: Preferences) = SetModeUseCase(preferences)

//    @MainScope
    @Provides
    @Singleton
    fun provideObserveFollowSystemModeUseCase(preferences: Preferences) =
        ObserveFollowSystemModeUseCase(preferences)
}
