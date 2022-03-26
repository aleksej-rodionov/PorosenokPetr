package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseAdapter
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

class SettingsAdapter(
    checkSwitch: (type: SettingsSwitchType, state: Boolean) -> Unit = { _, _ -> },
    checkSwitchWithTime: (millisFromDayBeginning: Long, state: Boolean) -> Unit = { _, _ -> },
    openTimePicker: () -> Unit = {}
): BaseAdapter(
    listOf(
        HeaderDelegate(),
        SwitchDelegate(checkSwitch),
        DoubleSwitchDelegate(checkSwitch),
        SwitchWithTimeDelegate(checkSwitchWithTime, openTimePicker)
    )
)