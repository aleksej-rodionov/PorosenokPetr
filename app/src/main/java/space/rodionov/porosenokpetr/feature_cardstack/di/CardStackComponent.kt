package space.rodionov.porosenokpetr.feature_cardstack.di

import dagger.Component
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardStackViewModel
import space.rodionov.porosenokpetr.main.di.AppComponent

@CardStackScope
@Component(
    dependencies = [AppComponent::class],
    modules = [CardStackModule::class]
)
interface CardStackComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): CardStackComponent
    }

    fun getCardStackViewModel(): CardStackViewModel
}