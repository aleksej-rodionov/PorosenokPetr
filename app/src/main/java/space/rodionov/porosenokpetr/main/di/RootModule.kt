package space.rodionov.porosenokpetr.main.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveFollowSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SetModeUseCase

@Module
class RootModule {

    @Provides
    @RootScope
    fun provideObserveModeUseCase(
        keyValueStorage: KeyValueStorage
    ) = ObserveModeUseCase(keyValueStorage)

    @Provides
    @RootScope
    fun provideSetModeUseCase(
        keyValueStorage: KeyValueStorage
    ) = SetModeUseCase(keyValueStorage)

    @Provides
    @RootScope
    fun provideObserveFollowSystemModeUseCase(
        keyValueStorage: KeyValueStorage
    ) = ObserveFollowSystemModeUseCase(keyValueStorage)
}