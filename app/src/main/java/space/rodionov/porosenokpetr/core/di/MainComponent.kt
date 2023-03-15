package space.rodionov.porosenokpetr.core.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import space.rodionov.porosenokpetr.core.MainActivity
import space.rodionov.porosenokpetr.core.PorosenokPetrApp
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.CollectionFragment
import space.rodionov.porosenokpetr.feature_cardstack.presentation.DrillerFragment
import space.rodionov.porosenokpetr.feature_cardstack.presentation.FilterBottomSheet
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.SettingsFragment
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.TimePickerBottomSheet
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.language.LanguageBottomSheet
import space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.WordlistFragment
import space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.edit_add_word.WordlistBottomSheet
import javax.inject.Singleton

//todo :app module

@Singleton
@Component(
    modules = [
        ScopeModule::class,
        AppModule::class,
        ViewModelModule::class
    ]
)
interface MainComponent {

    fun inject(application: PorosenokPetrApp)
    fun inject(activity: MainActivity)
    fun inject(fragment: CollectionFragment)
    fun inject(fragment: DrillerFragment)
    fun inject(fragment: FilterBottomSheet)
    fun inject(fragment: SettingsFragment)
    fun inject(fragment: TimePickerBottomSheet)
    fun inject(fragment: WordlistFragment)
    fun inject(fragment: WordlistBottomSheet)
    fun inject(fragment: LanguageBottomSheet)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): MainComponent
    }
}





