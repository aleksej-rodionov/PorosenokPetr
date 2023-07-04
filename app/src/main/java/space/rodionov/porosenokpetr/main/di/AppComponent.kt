package space.rodionov.porosenokpetr.main.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.feature_cardstack.di.CardStackModule
import space.rodionov.porosenokpetr.feature_settings.di.SettingsModule
import space.rodionov.porosenokpetr.feature_splash.presentation.SplashCustomActivity
import space.rodionov.porosenokpetr.feature_vocabulary.di.VocabularyModule
import space.rodionov.porosenokpetr.main.PorosenokPetrApp
import space.rodionov.porosenokpetr.main.presentation.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
//        SplashCustomViewModelModule::class, //todo find how to remove
        MainModule::class, //todo find how to remove
//        MainViewModelModule::class, //todo find how to remove
        CardStackModule::class,
//        CardStackViewModelModule::class,
        VocabularyModule::class,
//        VocabularyViewModelModule::class,
//        SettingsModule::class,
//        SettingsViewModelModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun getPreferences(): Preferences

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(application: PorosenokPetrApp)
    fun inject(activity: MainActivity)
    fun inject(activity: SplashCustomActivity)
}





