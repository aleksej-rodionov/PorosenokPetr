package space.rodionov.porosenokpetr.feature_splash.di

import dagger.Component
import space.rodionov.porosenokpetr.main.di.AppComponent

@SplashScope
@Component(
    dependencies = [AppComponent::class],
    modules = [SplashCustomViewModelModule::class]
)
interface SplashCustomComponent