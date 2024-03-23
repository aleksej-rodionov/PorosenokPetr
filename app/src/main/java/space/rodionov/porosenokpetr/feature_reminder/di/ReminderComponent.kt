package space.rodionov.porosenokpetr.feature_reminder.di

import dagger.Component
import space.rodionov.porosenokpetr.main.di.AppComponent

@ReminderScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ReminderModule::class]
)
interface ReminderComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): ReminderComponent
    }
}