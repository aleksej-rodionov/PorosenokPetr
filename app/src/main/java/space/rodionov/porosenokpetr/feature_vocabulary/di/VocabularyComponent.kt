package space.rodionov.porosenokpetr.feature_vocabulary.di

import dagger.Component
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.screens.vocabulary.VocabularyViewModel
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.screens.wordeditor.WordEditorViewModel
import space.rodionov.porosenokpetr.main.di.AppComponent

@VocabularyScope
@Component(
    dependencies = [AppComponent::class],
    modules = [VocabularyModule::class]
)
interface VocabularyComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): VocabularyComponent
    }

    fun getVocabularyViewModel(): VocabularyViewModel
    fun getWordEditorViewModel(): WordEditorViewModel
}