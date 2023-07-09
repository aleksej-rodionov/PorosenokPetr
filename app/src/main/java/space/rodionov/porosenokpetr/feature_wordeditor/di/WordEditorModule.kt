package space.rodionov.porosenokpetr.feature_wordeditor.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.feature_vocabulary.di.VocabularyScope
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.WordEditorViewModel

@Module
class WordEditorModule {

    @Provides
    @VocabularyScope
    fun provideWordEditorViewModel(

    ) = WordEditorViewModel(

    )
}