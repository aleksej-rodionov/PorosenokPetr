package space.rodionov.porosenokpetr.core.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
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

    //todo move to another features
    @Provides
    @Singleton
    fun provideCardStackUseCases(
        repo: WordRepo,
    ): CardStackUseCases {
        return CardStackUseCases(
            getRandomWordUseCase = GetRandomWordUseCase(repo),
            isCategoryActiveUseCase = IsCategoryActiveUseCase(repo),
            getAllCatsNamesUseCase = GetAllCatsNamesUseCase(repo),
            getAllActiveCatsNamesUseCase = GetAllActiveCatsNamesUseCase(repo),
            observeAllCategoriesUseCase = ObserveAllCategoriesUseCase(repo),
            updateWordIsActiveUseCase = UpdateWordIsActiveUseCase(repo),
            getTenWordsUseCase = GetTenWordsUseCase(repo)
        )
    }

    //todo move to another features
    @Provides
    @Singleton
    fun provideVocabularyUseCases(
        repo: WordRepo,
    ): VocabularyUseCases {
        return VocabularyUseCases(
            updateWordUseCase = UpdateWordUseCase(repo),
            updateIsWordActiveUseCase = UpdateIsWordActiveUseCase(repo),
            observeWordUseCase = ObserveWordUseCase(repo),
            observeWordsSearchQueryUseCase = ObserveWordsSearchQueryUseCase(repo),
            observeAllCatsWithWordsUseCase = ObserveAllCatsWithWordsUseCase(repo),
            observeAllActiveCatsNamesUseCase = ObserveAllActiveCatsNamesUseCase(repo)
        )
    }

    //todo move to another features
    @Provides
    @Singleton
    fun provideSettingsUseCases(
        preferences: Preferences
    ): SettingsUseCases {
        return SettingsUseCases(
            updateLearnedLangUseCase = UpdateLearnedLangUseCase(preferences),
            updateNativeLangUseCase = UpdateNativeLangUseCase(preferences),
            saveTranslationDirectionUseCase = SaveTranslationDirectionUseCase(preferences),
            setFollowSystemModeUseCase = SetFollowSystemModeUseCase(preferences),
        )
    }
}