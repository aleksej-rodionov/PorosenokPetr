package space.rodionov.porosenokpetr.core.domain.use_case

data class PreferencesUseCases(
    val observeModeUseCase: ObserveModeUseCase,
    val observeFollowSystemModeUseCase: ObserveFollowSystemModeUseCase,
    val setModeUseCase: SetModeUseCase,
    val setFollowSystemModeUseCase: SetFollowSystemModeUseCase
)
