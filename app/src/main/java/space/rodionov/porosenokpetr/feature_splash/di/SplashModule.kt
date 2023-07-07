package space.rodionov.porosenokpetr.feature_splash.di

import android.app.Application
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.feature_splash.domain.use_case.SplashInteractor
import space.rodionov.porosenokpetr.feature_splash.presentation.SplashCustomViewModel

@Module
class SplashModule {

    @Provides
    @SplashScope
    fun provideSplashInteractor(
        repo: WordRepo,
        @SplashQualifier appScope: CoroutineScope,
        app: Application
    ) = SplashInteractor(
        repo,
        appScope,
        app
    )

    @Provides
    @SplashScope
    fun provideSplashViewModel(
        splashInteractor: SplashInteractor
    ) = SplashCustomViewModel(splashInteractor)
}