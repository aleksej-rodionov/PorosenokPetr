package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.ItemSettingsSwitchWithTimeBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.domain.models.MenuSwitchWithTimePicker
import space.rodionov.porosenokpetr.feature_driller.presentation.base.AdapterDelegate
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseViewHolder
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

class SwitchWithTimeViewHolder(
    parent: ViewGroup,
    val checkSwitch: (type: SettingsSwitchType, state: Boolean) -> Unit = { _, _ -> }
): BaseViewHolder(parent, R.layout.item_settings_switch_with_time) {
    lateinit var binding: ItemSettingsSwitchWithTimeBinding
    override fun bind(model: BaseModel, holder: BaseViewHolder) {
        binding = ItemSettingsSwitchWithTimeBinding.bind(itemView)
        with(binding) {
            model as MenuSwitchWithTimePicker
            switchView.setOnCheckedChangeListener(null)

            bindSwitchState(model.type, switchView)

            switchView
        }
    }

    fun bindSwitchState(type: SettingsSwitchType, switch: SwitchCompat) {
        when(type) {
            SettingsSwitchType.TRANSLATION_DIRECTION -> {
                switch.isChecked = translationDirectionBVH
            }
            SettingsSwitchType.NIGHT_MODE -> {}
            SettingsSwitchType.SYSTEM_MODE -> {}
            SettingsSwitchType.NOTIFICATIONS -> {}
        }
    }
}

class SwitchWithTimeDelegate(
    private val checkSwitch: (type: SettingsSwitchType, state: Boolean) -> Unit = { _, _ -> }
): AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder {
        return SwitchWithTimeViewHolder(parent, checkSwitch)
    }

    override fun isValidType(model: BaseModel): Boolean {
        return model is MenuSwitchWithTimePicker
    }
}