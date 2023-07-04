package space.rodionov.porosenokpetr.feature_settings.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsViewModel
import space.rodionov.porosenokpetr.main.di.ViewModelKey

//@Module
//abstract class SettingsViewModelModule {
//
//    @Binds
//    @SettingsScope
//    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SettingsViewModel::class)
//    internal abstract fun settingsViewModel(settingsViewModel: SettingsViewModel): ViewModel
//}