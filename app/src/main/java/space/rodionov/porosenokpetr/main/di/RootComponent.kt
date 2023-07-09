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

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): RootComponent
    }

    fun getMainViewModel(): RootViewModel

    fun inject(activity: RootActivity)
}