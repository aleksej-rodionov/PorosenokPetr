package space.rodionov.porosenokpetr.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import space.rodionov.porosenokpetr.main.presentation.MainViewModel
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_splash.presentation.SplashCustomViewModel
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(SplashCustomViewModel::class)
    internal abstract fun customSplashViewModel(splashCustomViewModel: SplashCustomViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VocabularyViewModel::class)
    internal abstract fun vocabularyViewModel(vocabularyViewModel: VocabularyViewModel): ViewModel
}





