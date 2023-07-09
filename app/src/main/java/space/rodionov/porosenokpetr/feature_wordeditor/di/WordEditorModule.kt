package space.rodionov.porosenokpetr.feature_wordeditor.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.WordEditorViewModel

@Module
class WordEditorModule {

    @Provides
    @WordEditorScope
    fun provideWordEditorViewModel(

    ) = WordEditorViewModel(

    )
}