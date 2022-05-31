package space.rodionov.porosenokpetr.ui_compose.collection.di

import dagger.Provides
import space.rodionov.porosenokpetr.feature_driller.di.GenericSavedStateViewModelFactory
import space.rodionov.porosenokpetr.ui_compose.collection.CollectionViewModelNew

@Module
class CollectionScreenModule {

    //
    @Provides
    @CollectionScreenScope
    fun provideViewModel(): CollectionViewModelNew = CollectionViewModelNew(
        GenericSavedStateViewModelFactory(assistedFactory, this)
    )
}