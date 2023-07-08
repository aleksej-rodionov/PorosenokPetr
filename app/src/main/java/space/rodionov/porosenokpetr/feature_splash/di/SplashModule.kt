package space.rodionov.porosenokpetr.feature_splash.di

import android.app.Application
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.feature_splash.domain.use_case.SplashInteractor
import space.rodionov.porosenokpetr.feature_splash.presentation.SplashCustomViewModel
import space.rodionov.porosenokpetr.main.di.AppCoroutineScopeQualifier

@Module
class SplashModule {

    @Provides
    @SplashScope
    fun provideSplashInteractor(
        repo: WordRepo,
        @AppCoroutineScopeQualifier appScope: CoroutineScope,
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