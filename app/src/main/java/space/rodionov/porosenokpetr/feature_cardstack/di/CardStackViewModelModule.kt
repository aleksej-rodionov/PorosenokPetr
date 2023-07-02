package space.rodionov.porosenokpetr.feature_cardstack.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardStackViewModel
import space.rodionov.porosenokpetr.main.di.ViewModelKey

@Module
abstract class CardStackViewModelModule {

    @Binds
    @CardStackScope
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CardStackViewModel::class)
    internal abstract fun cardStackViewModel(cardStackViewModel: CardStackViewModel): ViewModel
}