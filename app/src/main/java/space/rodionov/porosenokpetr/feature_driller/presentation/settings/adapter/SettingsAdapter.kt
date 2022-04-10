package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseAdapter
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsItemType

class SettingsAdapter(
    checkSwitch: (type: SettingsItemType, state: Boolean) -> Unit = { _, _ -> },
    onTimePickerClick: () -> Unit = {},
    onItemClick: (type: SettingsItemType) -> Unit = { _ -> }
): BaseAdapter(
    listOf(
        HeaderDelegate(),
        SwitchDelegate(checkSwitch),
        SwitchWithTimeDelegate(checkSwitch, onTimePickerClick),
        ItemDelegate(onItemClick)
    )
)