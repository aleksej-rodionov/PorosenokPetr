package space.rodionov.porosenokpetr.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import space.rodionov.porosenokpetr.core.MainViewModel
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import javax.inject.Singleton

//todo :app module

@Module
abstract class ViewModelModule {

    @Binds
    @Singleton
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel
}





