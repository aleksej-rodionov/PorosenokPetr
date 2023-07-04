package space.rodionov.porosenokpetr.feature_vocabulary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyViewModel
import space.rodionov.porosenokpetr.main.di.ViewModelKey

//@Module
//abstract class VocabularyViewModelModule {
//
//    @Binds
//    @VocabularyScope
//    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(VocabularyViewModel::class)
//    internal abstract fun vocabularyViewModel(vocabularyViewModel: VocabularyViewModel): ViewModel
//}