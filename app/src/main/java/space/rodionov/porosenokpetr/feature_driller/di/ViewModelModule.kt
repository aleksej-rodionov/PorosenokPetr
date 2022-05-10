package space.rodionov.porosenokpetr.feature_driller.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import space.rodionov.porosenokpetr.MainViewModel
import space.rodionov.porosenokpetr.feature_driller.presentation.collection.CollectionViewModel
import space.rodionov.porosenokpetr.feature_driller.presentation.driller.DrillerViewModel
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.SettingsViewModel
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.language.LanguageBottomsheetViewModel
import space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.WordlistViewModel
import space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.edit_add_word.EditAddWordViewModel
import javax.inject.Singleton

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





