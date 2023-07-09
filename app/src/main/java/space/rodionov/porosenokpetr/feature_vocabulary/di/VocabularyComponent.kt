package space.rodionov.porosenokpetr.feature_vocabulary.di

import dagger.Component
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyViewModel
import space.rodionov.porosenokpetr.main.di.AppComponent

@VocabularyScope
@Component(
    dependencies = [AppComponent::class],
    modules = [VocabularyModule::class]
)
interface VocabularyComponent {

    fun getVocabularyViewModel(): VocabularyViewModel

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): VocabularyComponent
    }
}