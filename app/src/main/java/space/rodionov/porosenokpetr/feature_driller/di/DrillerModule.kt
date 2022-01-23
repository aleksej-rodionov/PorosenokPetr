package space.rodionov.porosenokpetr.feature_driller.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import space.rodionov.porosenokpetr.feature_driller.Constants
import space.rodionov.porosenokpetr.feature_driller.data.local.WordDao
import space.rodionov.porosenokpetr.feature_driller.data.local.WordDatabase
import space.rodionov.porosenokpetr.feature_driller.data.local.repository.WordRepoImpl
import space.rodionov.porosenokpetr.feature_driller.data.storage.Storage
import space.rodionov.porosenokpetr.feature_driller.data.storage.StorageImpl
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.GetTenWordsUseCase
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DrillerModule {

    @Provides
    @Singleton
    fun provideGetTenWordsUseCase(repo: WordRepo): GetTenWordsUseCase {
        return GetTenWordsUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideRepo(db: WordDatabase, storage: Storage): WordRepo {
        return WordRepoImpl(db.dao, storage)
    }

    @Provides
    @Singleton
    fun provideStorage(app: Application): Storage {
        return StorageImpl(app)
    }

    @Provides
    @Singleton
    fun provideDB(
        app: Application,
        callback: WordDatabase.Callback
    ): WordDatabase {
        return Room.databaseBuilder(app, WordDatabase::class.java, Constants.WORD_DB)
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope