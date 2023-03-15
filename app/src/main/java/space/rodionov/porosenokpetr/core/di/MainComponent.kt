package space.rodionov.porosenokpetr.core.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import space.rodionov.porosenokpetr.core.MainActivity
import space.rodionov.porosenokpetr.core.PorosenokPetrApp
import space.rodionov.porosenokpetr.feature_cardstack.di.CardStackModule
import space.rodionov.porosenokpetr.feature_settings.di.SettingsModule
import space.rodionov.porosenokpetr.feature_vocabulary.di.VocabularyModule
import javax.inject.Singleton

//todo :app module

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

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): MainComponent
    }
}





