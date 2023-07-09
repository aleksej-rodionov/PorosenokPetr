package space.rodionov.porosenokpetr.feature_wordeditor.di

import dagger.Component
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.WordEditorViewModel
import space.rodionov.porosenokpetr.main.di.AppComponent

@WordEditorScope
@Component(
    dependencies = [AppComponent::class],
    modules = [WordEditorModule::class]
)
interface WordEditorComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): WordEditorComponent
    }

    fun getWordEditorViewModel(): WordEditorViewModel
}