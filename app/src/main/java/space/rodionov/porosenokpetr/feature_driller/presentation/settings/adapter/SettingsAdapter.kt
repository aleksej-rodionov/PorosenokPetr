package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseAdapter
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

class SettingsAdapter(
    checkSwitch: (type: SettingsSwitchType, state: Boolean) -> Unit = { _, _ -> }
): BaseAdapter(
    listOf(
        HeaderDelegate(),
        SwitchDelegate(checkSwitch),
        DoubleSwitchDelegate(checkSwitch),
        SwitchWithTimeDelegate(checkSwitch)
    )
)