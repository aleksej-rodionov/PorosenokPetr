package space.rodionov.porosenokpetr.feature_driller.di

import android.app.Application
import androidx.room.Room
import androidx.work.WorkerParameters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.data.local.WordDatabase
import space.rodionov.porosenokpetr.feature_driller.data.repository.WordRepoImpl
import space.rodionov.porosenokpetr.feature_driller.data.storage.Datastore
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.work.NotificationHelper
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DrillerModule {


//    @Provides
//    @Singleton
//    fun provideWorkerParameters() = WorkerParameters

    @Provides
    @Singleton
    fun provideNotificationHelper(app: Application): NotificationHelper =
        NotificationHelper(app)

    @Provides
    @Singleton
    fun provideRepo(db: WordDatabase, datastore: Datastore): WordRepo {
        return WordRepoImpl(db.dao, datastore)
    }

    @Provides
    @Singleton
    fun provideDatastore(app: Application): Datastore {
        return Datastore(app)
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
    fun provideDrillerUseCases(repo: WordRepo): DrillerUseCases {
        return DrillerUseCases(
            updateWordUseCase = UpdateWordUseCase(repo),
            observeLearnedLangUseCase = ObserveLearnedLangUseCase(repo),
            updateLearnedLangUseCase = UpdateLearnedLangUseCase(repo),
            observeNativeLangUseCase = ObserveNativeLangUseCase(repo),
            updateNativeLangUseCase = UpdateNativeLangUseCase(repo),
            observeNotificationMillisUseCase = ObserveNotificationMillisUseCase(repo),
            setNotificationMillisUseCase = SetNotificationMillisUseCase(repo),
            observeReminderUseCase = ObserveReminderUseCase(repo),
            setReminderUseCase = SetReminderUseCase(repo),
            observeModeUseCase = ObserveModeUseCase(repo),
            observeFollowSystemModeUseCase = ObserveFollowSystemModeUseCase(repo),
            setModeUseCase = SetModeUseCase(repo),
            setFollowSystemModeUseCase = SetFollowSystemModeUseCase(repo),
            saveTranslationDirectionUseCase = SaveTranslationDirectionUseCase(repo),
            observeTranslationDirectionUseCase = ObserveTranslationDirectionUseCase(repo),
            updateIsWordActiveUseCase = UpdateIsWordActiveUseCase(repo),
            observeWordUseCase = ObserveWordUseCase(repo),
            updateCatNameStorageUseCase = UpdateCatNameStorageUseCase(repo),
            catNameFromStorageUseCase = CatNameFromStorageUseCase(repo),
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

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope