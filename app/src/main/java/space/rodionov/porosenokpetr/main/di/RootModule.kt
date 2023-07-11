package space.rodionov.porosenokpetr.main.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.CollectIsFollowingSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectNativeLanguageUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateModeUseCase

@Module
class RootModule {

    @Provides
    @RootScope
    fun provideCollectModeUseCase(
        keyValueStorage: KeyValueStorage
    ) = CollectModeUseCase(keyValueStorage)

    @Provides
    @RootScope
    fun provideUpdateModeUseCase(
        keyValueStorage: KeyValueStorage
    ) = UpdateModeUseCase(keyValueStorage)

    @Provides
    @RootScope
    fun provideCollectFollowSystemModeUseCase(
        keyValueStorage: KeyValueStorage
    ) = CollectIsFollowingSystemModeUseCase(keyValueStorage)

    @Provides
    @RootScope
    fun provideCollectNativeLanguageUseCase(
        keyValueStorage: KeyValueStorage
    ) = CollectNativeLanguageUseCase(keyValueStorage)
}