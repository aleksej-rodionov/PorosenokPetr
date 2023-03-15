package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

data class SettingsUseCases(
    val updateLearnedLangUseCase: UpdateLearnedLangUseCase,
    val updateNativeLangUseCase: UpdateNativeLangUseCase,
    val setFollowSystemModeUseCase: SetFollowSystemModeUseCase,
    val saveTranslationDirectionUseCase: SaveTranslationDirectionUseCase
)
