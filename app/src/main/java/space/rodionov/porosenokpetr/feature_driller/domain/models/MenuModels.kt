package space.rodionov.porosenokpetr.feature_driller.domain.models

import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

data class MenuSwitch(
//    val headerId: Int,
//    val tag: String,
    val descriptionId: Int?,
    val type: SettingsSwitchType,
    val titleId: Int,
    val switchState: Boolean,
    val isBlocked: Boolean = false
): BaseModel

data class MenuSwitchWithTimePicker(
//    val headerId: Int,
//    val tag: String,
    val descriptionId: Int?,
    val type: SettingsSwitchType,
    val titleId: Int,
//    val titleTag: String,
    val switchState: Boolean,
    val millisSinceDayBeginning: Long
): BaseModel