package space.rodionov.porosenokpetr.core.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import space.rodionov.porosenokpetr.core.data.local.WordDatabase
import space.rodionov.porosenokpetr.core.data.preferences.PreferencesImpl
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.core.domain.use_case.*
import space.rodionov.porosenokpetr.core.data.repository.WordRepoImpl
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.core.util.Constants
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.*
import space.rodionov.porosenokpetr.feature_settings.reminder.NotificationHelper
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.*
import space.rodionov.porosenokpetr.feature_vocabulary.domaion.use_case.*
import javax.inject.Singleton

//todo :app module

@Module
class AppModule {

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideNotificationHelper(app: Application): NotificationHelper =
        NotificationHelper(app)

    @Provides
    @Singleton
    fun provideRepo(db: WordDatabase): WordRepo {
        return WordRepoImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideDatastore(app: Application): Preferences {
        return PreferencesImpl(app)
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

    @Provides
    @Singleton
    fun provideSharedUseCases(
        repo: WordRepo,
        preferences: Preferences
    ): SharedUseCases { // todo provide each separately
        return SharedUseCases(
            observeModeUseCase = ObserveModeUseCase(preferences),
            observeFollowSystemModeUseCase = ObserveFollowSystemModeUseCase(preferences),
            setModeUseCase = SetModeUseCase(preferences),
            observeLearnedLangUseCase = ObserveLearnedLangUseCase(preferences),
            observeNativeLangUseCase = ObserveNativeLangUseCase(preferences),
            observeTranslationDirectionUseCase = ObserveTranslationDirectionUseCase(preferences),
            makeCategoryActiveUseCase = MakeCategoryActiveUseCase(repo)
        )
    }
}