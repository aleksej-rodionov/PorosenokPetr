package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.getLocalizedString
import space.rodionov.porosenokpetr.databinding.ItemSettingsSwitchBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.domain.models.MenuSwitch
import space.rodionov.porosenokpetr.feature_driller.presentation.base.AdapterDelegate
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseViewHolder
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType
import java.util.*


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

            bindSwitchState(model, switchView)

            switchView.setOnCheckedChangeListener { _, isChecked ->
                checkSwitch(model.type, isChecked)
            }
        }
    }

    private fun bindSwitchState(model: BaseModel, switch: SwitchCompat) {
        model as MenuSwitch
        when(model.type) {
            SettingsSwitchType.TRANSLATION_DIRECTION -> {
                switch.isChecked = model.switchState
                var transDirText = if (model.switchState) res.getString(R.string.from_ru_to_en)
                    else res.getString(R.string.from_en_to_ru)
                switch.text = transDirText
            }
            SettingsSwitchType.NIGHT_MODE -> {
                switch.isChecked = model.switchState
                switch.text = res.getString(R.string.dark_mode)
                if (model.isBlocked) {
                    switch.setTextColor(res.getColor(R.color.gray600))
                    switch.thumbTintList = ColorStateList.valueOf(res.getColor(R.color.gray600))
                    switch.isEnabled = false
                } else {
                    switch.setTextColor(colors[3])
                    switch.thumbTintList = ColorStateList.valueOf(res.getColor(R.color.white))
                    switch.isEnabled = true
                }
            }
            SettingsSwitchType.SYSTEM_MODE -> {
                switch.isChecked = model.switchState
                switch.text = res.getString(R.string.follow_system_mode)
                switch.setTextColor(colors[3])//todo костыль (почемуто иногда блокируется этот switch, хотя isBlocked приходит false ВСЕГДА)
                switch.thumbTintList = ColorStateList.valueOf(res.getColor(R.color.white))//todo костыль (и только костыль пока-что помог)
                switch.isEnabled = true //todo костыль
            }
            SettingsSwitchType.NATIVE_LANG -> {
                switch.isChecked = model.switchState
                val resId = if (model.switchState) R.string.ukrainian else R.string.russian
                val requestedLang = if (nativeLangBVH == 1) "uk" else "ru"
                switch.text = getLocalizedString(
                    Locale(requestedLang),
                    resId,
                    itemView.context
                )
                switch.setTextColor(colors[3])//todo костыль (почемуто иногда блокируется этот switch, хотя isBlocked приходит false ВСЕГДА)
                switch.thumbTintList = ColorStateList.valueOf(res.getColor(R.color.white))//todo костыль (и только костыль пока-что помог)
                switch.isEnabled = true //todo костыль
            }
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