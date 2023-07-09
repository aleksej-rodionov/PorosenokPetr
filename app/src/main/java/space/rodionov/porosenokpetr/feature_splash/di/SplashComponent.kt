package space.rodionov.porosenokpetr.feature_splash.di

import dagger.Component
import space.rodionov.porosenokpetr.feature_splash.presentation.SplashCustomActivity
import space.rodionov.porosenokpetr.feature_splash.presentation.SplashCustomViewModel
import space.rodionov.porosenokpetr.main.di.AppComponent

@SplashScope
@Component(
    dependencies = [AppComponent::class],
    modules = [SplashModule::class, SplashViewModelModule::class]
)
interface SplashComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): SplashComponent
    }

    fun getSplashViewModel(): SplashCustomViewModel

    fun inject(activity: SplashCustomActivity)
}