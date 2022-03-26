package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.ItemSettingsDoubleSwitchBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.domain.models.MenuDoubleSwitch
import space.rodionov.porosenokpetr.feature_driller.presentation.base.AdapterDelegate
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseViewHolder
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

class DoubleSwitchViewHolder(
    val parent: ViewGroup,
    val checkSwitch: (type: SettingsSwitchType, state: Boolean) -> Unit = { _, _ -> }
): BaseViewHolder( parent, R.layout.item_settings_double_switch) {
    lateinit var binding: ItemSettingsDoubleSwitchBinding
    override fun bind(model: BaseModel, holder: BaseViewHolder) {
        binding = ItemSettingsDoubleSwitchBinding.bind(itemView)
        with(binding) {
            model as MenuDoubleSwitch
            switchViewFirst.setOnCheckedChangeListener(null)
            switchViewSecond.setOnCheckedChangeListener(null)
            model.descriptionFirstId?.let { descId ->
                tvDescriptionFirst.visibility = View.VISIBLE
                tvDescriptionFirst.text = res.getString(descId)
            }
            model.descriptionSecondId?.let { descId ->
                tvDescriptionSecond.visibility = View.VISIBLE
                tvDescriptionSecond.text = res.getString(descId)
            }

            bindSwitchState(model.typeFirst, switchViewFirst)
            bindSwitchState(model.typeSecond, switchViewSecond)

            switchViewFirst.setOnCheckedChangeListener { _, isChecked ->
                checkSwitch(model.typeFirst, isChecked)
            }
            switchViewSecond.setOnCheckedChangeListener { _, isChecked ->
                checkSwitch(model.typeSecond, isChecked)
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


class DoubleSwitchDelegate(
    private val checkSwitch: (type: SettingsSwitchType, state: Boolean) -> Unit = { _, _ -> }
): AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder {
        return DoubleSwitchViewHolder(parent, checkSwitch)
    }

    override fun isValidType(model: BaseModel): Boolean {
        return model is MenuDoubleSwitch
    }
}