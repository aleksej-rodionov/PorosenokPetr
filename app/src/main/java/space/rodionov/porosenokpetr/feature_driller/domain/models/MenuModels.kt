package space.rodionov.porosenokpetr.feature_driller.domain.models

import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

data class MenuSwitch(
    val headerId: Int,
    val descriptionId: Int?,
    val type: SettingsSwitchType,
    val titleId: Int,
    val switchState: Boolean
): BaseModel

data class MenuDoubleSwitch(
    val headerId: Int,
    val descriptionFirstId: Int?,
    val descriptionSecondId: Int?,
    val typeFirst: SettingsSwitchType,
    val typeSecond: SettingsSwitchType,
    val titleFirstId: Int,
    val titleSecondId: Int,
    val switchStateFirst: Boolean,
    val switchStateSecond: Boolean
): BaseModel

data class MenuSwitchWithTimePicker(
    val headerId: Int,
    val descriptionId: Int?,
    val type: SettingsSwitchType,
    val titleId: Int,
    val switchState: Boolean,
    val timestamp: Long
): BaseModel