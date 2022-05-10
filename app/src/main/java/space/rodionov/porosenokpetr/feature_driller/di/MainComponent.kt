package space.rodionov.porosenokpetr.feature_driller.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import space.rodionov.porosenokpetr.MainActivity
import space.rodionov.porosenokpetr.PorosenokPetrApp
import space.rodionov.porosenokpetr.feature_driller.presentation.collection.CollectionFragment
import space.rodionov.porosenokpetr.feature_driller.presentation.driller.DrillerFragment
import space.rodionov.porosenokpetr.feature_driller.presentation.driller.FilterBottomSheet
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.SettingsFragment
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.TimePickerBottomSheet
import space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.WordlistFragment
import space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.edit_add_word.WordlistBottomSheet
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ScopeModule::class,
        AppModule::class
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

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): MainComponent
    }
}





