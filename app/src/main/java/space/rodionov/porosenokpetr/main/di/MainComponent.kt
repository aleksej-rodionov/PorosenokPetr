package space.rodionov.porosenokpetr.main.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import space.rodionov.porosenokpetr.main.presentation.MainActivity
import space.rodionov.porosenokpetr.main.PorosenokPetrApp
import space.rodionov.porosenokpetr.feature_cardstack.di.CardStackModule
import space.rodionov.porosenokpetr.feature_settings.di.SettingsModule
import space.rodionov.porosenokpetr.feature_splash.presentation.SplashCustomActivity
import space.rodionov.porosenokpetr.feature_vocabulary.di.VocabularyModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CardStackModule::class,
        VocabularyModule::class,
        SettingsModule::class,
        ViewModelModule::class
    ]
)
interface MainComponent {

    fun inject(application: PorosenokPetrApp)
    fun inject(activity: MainActivity)
    fun inject(activity: SplashCustomActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): MainComponent
    }
}





