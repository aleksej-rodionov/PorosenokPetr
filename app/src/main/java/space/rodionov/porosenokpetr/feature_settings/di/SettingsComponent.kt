package space.rodionov.porosenokpetr.feature_settings.di

import dagger.Component
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsViewModel
import space.rodionov.porosenokpetr.main.di.AppComponent

@SettingsScope
@Component(
    dependencies = [AppComponent::class],
    modules = [SettingsModule::class]
)
interface SettingsComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): SettingsComponent
    }

    fun getSettingsViewModel(): SettingsViewModel
}