package space.rodionov.porosenokpetr.feature_driller.domain.models

import space.rodionov.porosenokpetr.feature_driller.presentation.settings.language.LanguageItem
import space.rodionov.porosenokpetr.feature_driller.utils.LocalizedString
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsItemType

data class MenuLanguage(
    val type: SettingsItemType,
    val title: LocalizedString,
    val language: LanguageItem
): BaseModel

data class MenuSwitch(
    val descriptionId: Int?,
    val type: SettingsItemType,
    val text: LocalizedString,
    val switchState: Boolean,
    val isBlocked: Boolean = false
): BaseModel

data class MenuSwitchWithTimePicker(
    val descriptionId: Int?,
    val type: SettingsItemType,
    val text: LocalizedString,
    val switchState: Boolean,
    val millisSinceDayBeginning: Long
): BaseModel