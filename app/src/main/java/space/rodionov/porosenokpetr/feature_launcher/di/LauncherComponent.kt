package space.rodionov.porosenokpetr.feature_launcher.di

import dagger.Component
import space.rodionov.porosenokpetr.feature_launcher.presentation.LauncherActivity
import space.rodionov.porosenokpetr.feature_launcher.presentation.LauncherViewModel
import space.rodionov.porosenokpetr.main.di.AppComponent

@LauncherScope
@Component(
    dependencies = [AppComponent::class],
    modules = [LauncherModule::class, LauncherViewModelModule::class]
)
interface LauncherComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): LauncherComponent
    }

    fun getLauncherViewModel(): LauncherViewModel

    fun inject(activity: LauncherActivity)
}