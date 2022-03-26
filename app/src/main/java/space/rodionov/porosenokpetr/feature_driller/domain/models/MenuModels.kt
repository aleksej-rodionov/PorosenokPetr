package space.rodionov.porosenokpetr.feature_driller.domain.models

import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

data class MenuSwitch(
    val descriptionId: Int?,
    val type: SettingsSwitchType,
    val titleId: Int,
    val switchState: Boolean
): BaseModel

data class MenuDoubleSwitch(
    val descriptionFirstId: Int?,
    val descriptionSecondId: Int?,
    val typeFirst: SettingsSwitchType,
    val typeSecond: SettingsSwitchType,
    val titleId: Int,
    val switchStateFirst: Boolean,
    val switchStateSecond: Boolean
): BaseModel

data class MenuSwitchWithTimePicker(
    val descriptionId: Int?,
    val type: SettingsSwitchType,
    val titleId: Int,
    val switchState: Boolean,
    val timestamp: Long
): BaseModel