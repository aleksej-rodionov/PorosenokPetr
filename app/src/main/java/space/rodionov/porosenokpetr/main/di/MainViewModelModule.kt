package space.rodionov.porosenokpetr.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.main.presentation.MainViewModel
import javax.inject.Singleton

//@Module
//abstract class MainViewModelModule {
//
//    @Binds
////    @MainScope
//    @Singleton
//    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel::class)
//    internal abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel
//}