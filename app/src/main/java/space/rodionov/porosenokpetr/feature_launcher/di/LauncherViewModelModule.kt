package space.rodionov.porosenokpetr.feature_launcher.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_launcher.presentation.LauncherViewModel
import space.rodionov.porosenokpetr.main.di.ViewModelKey

@Module
abstract class LauncherViewModelModule {

    @Binds
    @LauncherScope
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LauncherViewModel::class)
    internal abstract fun launcherViewModel(launcherViewModel: LauncherViewModel): ViewModel
}