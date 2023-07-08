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
import space.rodionov.porosenokpetr.core.util.Constants
import space.rodionov.porosenokpetr.core.util.SwedishSpeaker
import javax.inject.Singleton

@Module
class AppModule {

    @AppCoroutineScopeQualifier
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideTextToSpeechInitListener(): TextToSpeech.OnInitListener {
        return TextToSpeech.OnInitListener {
            TODO("Not yet implemented")
        }
    }

    @Provides
    @Singleton
    fun provideTextToSpeech(
        context: Application
    ): SwedishSpeaker {
        return SwedishSpeaker(context)
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