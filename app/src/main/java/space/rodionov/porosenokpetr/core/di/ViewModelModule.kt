package space.rodionov.porosenokpetr.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import space.rodionov.porosenokpetr.MainViewModel
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.SettingsViewModel
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

//    @Binds
//    @IntoMap
//    @ViewModelKey(CollectionViewModel::class)
//    internal abstract fun collectionViewModel(collectionViewModel: CollectionViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(DrillerViewModel::class)
//    internal abstract fun drillerViewModel(drillerViewModel: DrillerViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(LanguageBottomsheetViewModel::class)
//    internal abstract fun languageBottomsheetViewModel(languageBottomsheetViewModel: LanguageBottomsheetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun settingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(EditAddWordViewModel::class)
//    internal abstract fun editAddWordViewModel(editAddWordViewModel: EditAddWordViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(WordlistViewModel::class)
//    internal abstract fun wordlistViewModel(wordlistViewModel: WordlistViewModel): ViewModel
}





