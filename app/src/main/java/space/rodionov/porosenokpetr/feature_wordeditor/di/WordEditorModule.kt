package space.rodionov.porosenokpetr.feature_wordeditor.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.domain.use_case.GetWordByIdUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateWordUseCase
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.WordEditorViewModel

@Module
class WordEditorModule {

    @Provides
    @WordEditorScope
    fun provideGetWordByIdUseCase(repo: WordRepo) = GetWordByIdUseCase(repo)

    @Provides
    @WordEditorScope
    fun provideUpdateWordUseCase(repo: WordRepo) = UpdateWordUseCase(repo)

    @Provides
    @WordEditorScope
    fun provideWordEditorViewModel(
        getWordByIdUseCase: GetWordByIdUseCase,
        updateWordUseCase: UpdateWordUseCase
    ) = WordEditorViewModel(
        getWordByIdUseCase,
        updateWordUseCase
    )
}