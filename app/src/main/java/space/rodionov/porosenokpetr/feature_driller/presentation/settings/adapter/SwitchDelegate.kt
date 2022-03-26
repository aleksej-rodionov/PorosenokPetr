package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.ItemSettingsSwitchBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.domain.models.MenuSwitch
import space.rodionov.porosenokpetr.feature_driller.presentation.base.AdapterDelegate
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseViewHolder
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType


class SwitchViewHolder(
    val parent: ViewGroup,
    val checkSwitch: (type: SettingsSwitchType, state: Boolean) -> Unit = { _, _ -> }
) : BaseViewHolder(parent, R.layout.item_settings_switch) {
    lateinit var binding: ItemSettingsSwitchBinding
    override fun bind(model: BaseModel, holder: BaseViewHolder) {
        binding = ItemSettingsSwitchBinding.bind(itemView)
        with(binding) {
            model as MenuSwitch
            switchView.setOnCheckedChangeListener(null)
            model.descriptionId?.let { descId ->
                tvDescription.visibility = View.VISIBLE
                tvDescription.text = itemView.resources.getString(descId)
            }

            bindSwitchState(model.type, switchView)

            switchView.setOnCheckedChangeListener { _, isChecked ->
                checkSwitch(model.type, isChecked)
            }
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


class SwitchDelegate(
    private val checkSwitch: (type: SettingsSwitchType, state: Boolean) -> Unit = { _, _ -> }
): AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder {
        return SwitchViewHolder(parent, checkSwitch)
    }

    override fun isValidType(model: BaseModel): Boolean {
        return model is MenuSwitch
    }
}