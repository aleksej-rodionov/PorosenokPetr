package space.rodionov.porosenokpetr.core.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.data.local.WordDatabase
import space.rodionov.porosenokpetr.core.data.preferences.PreferencesImpl
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.core.domain.use_case.*
import space.rodionov.porosenokpetr.feature_driller.data.repository.WordRepoImpl
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.work.NotificationHelper
import javax.inject.Singleton

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
    fun providePreferencesUseCases(preferences: Preferences): PreferencesUseCases {
        return PreferencesUseCases(
            observeModeUseCase = ObserveModeUseCase(preferences),
            observeFollowSystemModeUseCase = ObserveFollowSystemModeUseCase(preferences),
            setModeUseCase = SetModeUseCase(preferences),
            setFollowSystemModeUseCase = SetFollowSystemModeUseCase(preferences),
        )
    }


    //todo move to another features
    @Provides
    @Singleton
    fun provideDrillerUseCases(
        repo: WordRepo,
        preferences: Preferences
    ): DrillerUseCases {
        return DrillerUseCases(
            observeLearnedLangUseCase = ObserveLearnedLangUseCase(preferences),
            updateLearnedLangUseCase = UpdateLearnedLangUseCase(preferences),
            observeNativeLangUseCase = ObserveNativeLangUseCase(preferences),
            updateNativeLangUseCase = UpdateNativeLangUseCase(preferences),
            saveTranslationDirectionUseCase = SaveTranslationDirectionUseCase(preferences),
            observeTranslationDirectionUseCase = ObserveTranslationDirectionUseCase(preferences),

            updateWordUseCase = UpdateWordUseCase(repo),
            updateIsWordActiveUseCase = UpdateIsWordActiveUseCase(repo),
            observeWordUseCase = ObserveWordUseCase(repo),
            observeWordsSearchQueryUseCase = ObserveWordsSearchQueryUseCase(repo),
            observeAllCatsWithWordsUseCase = ObserveAllCatsWithWordsUseCase(repo),
            getCatCompletionPercentUseCase = GetCatCompletionPercentUseCase(repo),
            observeAllActiveCatsNamesUseCase = ObserveAllActiveCatsNamesUseCase(repo),
            getRandomWordUseCase = GetRandomWordUseCase(repo),
            isCategoryActiveUseCase = IsCategoryActiveUseCase(repo),
            getAllCatsNamesUseCase = GetAllCatsNamesUseCase(repo),
            getAllActiveCatsNamesUseCase = GetAllActiveCatsNamesUseCase(repo),
            makeCategoryActiveUseCase = MakeCategoryActiveUseCase(repo),
            observeAllCategoriesUseCase = ObserveAllCategoriesUseCase(repo),
            updateWordIsActiveUseCase = UpdateWordIsActiveUseCase(repo),
            getTenWordsUseCase = GetTenWordsUseCase(repo)
        )
    }
}