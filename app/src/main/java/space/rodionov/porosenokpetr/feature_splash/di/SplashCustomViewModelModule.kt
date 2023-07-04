package space.rodionov.porosenokpetr.feature_splash.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_splash.presentation.SplashCustomViewModel
import space.rodionov.porosenokpetr.main.di.ViewModelKey
import javax.inject.Singleton

//@Module
//abstract class SplashCustomViewModelModule {//todo back @SplashScope
//
//    @Binds
////    @SplashScope
//    @Singleton
//    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SplashCustomViewModel::class)
//    internal abstract fun customSplashViewModel(splashCustomViewModel: SplashCustomViewModel): ViewModel
//}