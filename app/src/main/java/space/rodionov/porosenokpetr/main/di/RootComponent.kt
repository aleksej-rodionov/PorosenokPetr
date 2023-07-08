package space.rodionov.porosenokpetr.main.di

import dagger.Component
import space.rodionov.porosenokpetr.main.presentation.RootActivity
import space.rodionov.porosenokpetr.main.presentation.RootViewModel

@RootScope
@Component(
    dependencies = [AppComponent::class],
    modules = [RootModule::class, RootViewModelModule::class]
)
interface RootComponent {

    fun getMainViewModel(): RootViewModel

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): RootComponent
    }

    fun inject(activity: RootActivity)
}