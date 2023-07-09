package space.rodionov.porosenokpetr.main.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.util.SwedishSpeaker
import space.rodionov.porosenokpetr.main.PorosenokPetrApp
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun getApplication(): Application
    fun getPreferences(): KeyValueStorage
    fun getWordRepo(): WordRepo
    fun getSwedishSpeaker(): SwedishSpeaker
    @AppCoroutineScopeQualifier
    fun getAppCoroutineScope(): CoroutineScope

    fun inject(application: PorosenokPetrApp)
}





