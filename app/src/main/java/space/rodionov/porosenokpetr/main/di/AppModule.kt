package space.rodionov.porosenokpetr.main.di

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import space.rodionov.porosenokpetr.core.data.local.WordDatabase
import space.rodionov.porosenokpetr.core.data.preferences.KeyValueStorageImpl
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.data.repository.WordRepoImpl
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.util.ForeignSpeaker
import space.rodionov.porosenokpetr.core.util.Constants
import javax.inject.Singleton

@Module
class AppModule {

    /**
     * Use this coroutine scope in places where you launch a long-running operation
     * that can be interrupted by killing the class it were run in.
     * E.g. you launch a long-running operation with database in a viewModel
     * and then immediately leave the screen and the viewModel dies
     */
    @AppCoroutineScopeQualifier
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideTextToSpeechInitListener(): TextToSpeech.OnInitListener {
        return TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                //todo handle the result
            } else {
                //todo handle the result
            }
        }
    }

    @Provides
    @Singleton
    fun provideTextToSpeech(
        context: Application
    ): ForeignSpeaker {
        return ForeignSpeaker(context)
    }

    @Provides
    @Singleton
    fun provideRepo(db: WordDatabase): WordRepo {
        return WordRepoImpl(db.dao)
    }

    @Provides
    @Singleton
    fun providePreferences(app: Application): KeyValueStorage {
        return KeyValueStorageImpl(app)
    }

    @Provides
    @Singleton
    fun provideDB(
        app: Application,
    ): WordDatabase {
        return Room.databaseBuilder(app, WordDatabase::class.java, Constants.WORD_DB)
            .fallbackToDestructiveMigration()
            .build()
    }
}