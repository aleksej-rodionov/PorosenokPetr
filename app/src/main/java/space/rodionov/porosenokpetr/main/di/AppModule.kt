package space.rodionov.porosenokpetr.main.di

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
import space.rodionov.porosenokpetr.core.util.Constants
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.GetAllCategoriesUseCase
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.ObserveAllCategoriesUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateWordStatusUseCase
import space.rodionov.porosenokpetr.feature_splash.domain.use_case.SplashInteractor
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateLearnedPercentInCategory
import javax.inject.Singleton

@Module
class AppModule {

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

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
//        callback: WordDatabase.Callback
    ): WordDatabase {
        return Room.databaseBuilder(app, WordDatabase::class.java, Constants.WORD_DB)
            .fallbackToDestructiveMigration()
//            .addCallback(callback)
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
            observeAllCategoriesUseCase = ObserveAllCategoriesUseCase(repo),
            getAllCategoriesUseCase = GetAllCategoriesUseCase(repo),
            updateWordStatusUseCase = UpdateWordStatusUseCase(repo),
            updateLearnedPercentInCategory = UpdateLearnedPercentInCategory(repo),
            makeCategoryActiveUseCase = MakeCategoryActiveUseCase(repo)
        )
    }

    //todo :app module
    @Provides
    @Singleton
    fun provideMainInteractor(
        repo: WordRepo,
        @ApplicationScope appScope: CoroutineScope,
        app: Application
    ) = SplashInteractor(
        repo,
        appScope,
        app
    )
}