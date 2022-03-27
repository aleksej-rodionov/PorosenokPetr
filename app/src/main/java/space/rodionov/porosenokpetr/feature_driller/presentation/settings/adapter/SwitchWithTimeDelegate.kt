package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import android.view.View
import android.view.ViewGroup
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.ItemSettingsSwitchWithTimeBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.domain.models.MenuSwitchWithTimePicker
import space.rodionov.porosenokpetr.feature_driller.presentation.base.AdapterDelegate
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseViewHolder
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

class SwitchWithTimeViewHolder(
    val parent: ViewGroup,
//    val checkSwitch: (millisFromDayBeginning: Long, state: Boolean) -> Unit = { _, _ -> },
    val checkSwitch: (type: SettingsSwitchType, state: Boolean) -> Unit = { _, _ -> },
    val onTimePickerClick: () -> Unit = {}
): BaseViewHolder(parent, R.layout.item_settings_switch_with_time) {
    lateinit var binding: ItemSettingsSwitchWithTimeBinding
    override fun bind(model: BaseModel, holder: BaseViewHolder) {
        binding = ItemSettingsSwitchWithTimeBinding.bind(itemView)
        with(binding) {
            model as MenuSwitchWithTimePicker
            switchView.setOnCheckedChangeListener(null)

//            switchView.isChecked = notifyBVH
//            timeSelectionView.visibility = if (notifyBVH) View.VISIBLE else View.GONE
            switchView.isChecked = model.switchState
            timeSelectionView.visibility = if (model.switchState) View.VISIBLE else View.GONE

            switchView.text = res.getString(R.string.remind)

            tvTime.text = model.millisSinceDayBeginning.toString()

            switchView.setOnCheckedChangeListener { _, isChecked ->
                checkSwitch(model.type, isChecked)
            }
            ivCalendar.setOnClickListener {
                onTimePickerClick()
            }
        }
    }
}

class SwitchWithTimeDelegate(
//    private val checkSwitch: (millisFromDayBeginning: Long, state: Boolean) -> Unit = { _, _ -> },
    private val checkSwitch: (type: SettingsSwitchType, state: Boolean) -> Unit = { _, _ -> },
    private val onTimePickerClick: () -> Unit = {}
): AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder {
        return SwitchWithTimeViewHolder(parent, checkSwitch, onTimePickerClick)
    }

    override fun isValidType(model: BaseModel): Boolean {
        return model is MenuSwitchWithTimePicker
    }
}