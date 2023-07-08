package space.rodionov.porosenokpetr.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.main.presentation.RootViewModel

@Module
abstract class RootViewModelModule {

    @Binds
    @RootScope
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RootViewModel::class)
    internal abstract fun rootViewModel(rootViewModel: RootViewModel): ViewModel
}