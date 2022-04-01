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

    @Provides
    @Singleton
    fun provideGetFollowSystemLocaleUseCase(repo: WordRepo): ObserveFollowSystemLocaleUseCase {
        return ObserveFollowSystemLocaleUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideSetFollowSystemLocaleUseCase(repo: WordRepo): SetFollowSystemLocaleUseCase {
        return SetFollowSystemLocaleUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideObserveNativeLangUseCase(repo: WordRepo): ObserveNativeLangUseCase {
        return ObserveNativeLangUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideUpdateNativeLangUseCase(repo: WordRepo): UpdateNativeLangUseCase {
        return UpdateNativeLangUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideObserveNotificationMillisUseCase(repo: WordRepo): ObserveNotificationMillisUseCase {
        return ObserveNotificationMillisUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideSetNotificationMillisUseCase(repo: WordRepo): SetNotificationMillisUseCase {
        return SetNotificationMillisUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideObserveReminderUseCase(repo: WordRepo): ObserveReminderUseCase {
        return ObserveReminderUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideSetReminderUseCase(repo: WordRepo): SetReminderUseCase {
        return SetReminderUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetModeUseCase(repo: WordRepo): ObserveModeUseCase {
        return ObserveModeUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetFollowSystemModeUseCase(repo: WordRepo): ObserveFollowSystemModeUseCase {
        return ObserveFollowSystemModeUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideSetModeUseCase(repo: WordRepo): SetModeUseCase {
        return SetModeUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideSetFollowSystemModeUseCase(repo: WordRepo): SetFollowSystemModeUseCase {
        return SetFollowSystemModeUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideSaveTranslationDirectionUseCase(repo: WordRepo): SaveTranslationDirectionUseCase {
        return SaveTranslationDirectionUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideObserveTranslationDirectionUseCase(repo: WordRepo): ObserveTranslationDirectionUseCase {
        return ObserveTranslationDirectionUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideUpdateIsWordActiveUseCase(repo: WordRepo): UpdateIsWordActiveUseCase {
        return UpdateIsWordActiveUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideObserveWordUseCase(repo: WordRepo): ObserveWordUseCase {
        return ObserveWordUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideUpdateCatNameStorageUseCase(repo: WordRepo): UpdateCatNameStorageUseCase {
        return UpdateCatNameStorageUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideCatNameFromStorageUseCase(repo: WordRepo): CatNameFromStorageUseCase {
        return CatNameFromStorageUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideObserveWordsSearchQueryUseCase(repo: WordRepo): ObserveWordsSearchQueryUseCase {
        return ObserveWordsSearchQueryUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideObserveAllCatsWithWordsUseCase(repo: WordRepo): ObserveAllCatsWithWordsUseCase {
        return ObserveAllCatsWithWordsUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetCatCompletionPercentUseCase(repo: WordRepo): GetCatCompletionPercentUseCase {
        return GetCatCompletionPercentUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideObserveAllActiveCatsNamesUseCase(repo: WordRepo): ObserveAllActiveCatsNamesUseCase {
        return ObserveAllActiveCatsNamesUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetRandomWordUseCase(repo: WordRepo): GetRandomWordUseCase {
        return GetRandomWordUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideIsCategoryActiveUseCase(repo: WordRepo): IsCategoryActiveUseCase {
        return IsCategoryActiveUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetAllCatsNamesUseCase(repo: WordRepo): GetAllCatsNamesUseCase {
        return GetAllCatsNamesUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetAllActiveCatsNamesUseCase(repo: WordRepo): GetAllActiveCatsNamesUseCase {
        return GetAllActiveCatsNamesUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideMakeCategoryActiveUseCase(repo: WordRepo): MakeCategoryActiveUseCase {
        return MakeCategoryActiveUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideObserveAllCategoriesUseCase(repo: WordRepo): ObserveAllCategoriesUseCase {
        return ObserveAllCategoriesUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideUpdateWordIsActiveUseCase(repo: WordRepo): UpdateWordIsActiveUseCase {
        return UpdateWordIsActiveUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetTenWordsUseCase(repo: WordRepo): GetTenWordsUseCase {
        return GetTenWordsUseCase(repo)
    }

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

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope