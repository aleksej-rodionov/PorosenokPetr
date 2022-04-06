package space.rodionov.porosenokpetr.feature_driller.domain.models

import space.rodionov.porosenokpetr.feature_driller.utils.LocalizedString
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

data class MenuSwitch(
    val descriptionId: Int?,
    val type: SettingsSwitchType,
    val text: LocalizedString,
    val switchState: Boolean,
    val isBlocked: Boolean = false
): BaseModel

data class MenuSwitchWithTimePicker(
    val descriptionId: Int?,
    val type: SettingsSwitchType,
    val text: LocalizedString,
    val switchState: Boolean,
    val millisSinceDayBeginning: Long
): BaseModel