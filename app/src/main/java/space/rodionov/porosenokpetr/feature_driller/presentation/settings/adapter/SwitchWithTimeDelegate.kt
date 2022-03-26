package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import android.opengl.Visibility
import android.view.View
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
    val parent: ViewGroup,
    val checkSwitch: (millisFromDayBeginning: Long, state: Boolean) -> Unit = { _, _ -> },
    val openTimePicker: () -> Unit = {}
): BaseViewHolder(parent, R.layout.item_settings_switch_with_time) {
    lateinit var binding: ItemSettingsSwitchWithTimeBinding
    override fun bind(model: BaseModel, holder: BaseViewHolder) {
        binding = ItemSettingsSwitchWithTimeBinding.bind(itemView)
        with(binding) {
            model as MenuSwitchWithTimePicker
            switchView.setOnCheckedChangeListener(null)

            switchView.isChecked = notifyBVH
            timeSelectionView.visibility = if (notifyBVH) View.VISIBLE else View.GONE

            switchView
        }
    }
}

class SwitchWithTimeDelegate(
    private val checkSwitch: (millisFromDayBeginning: Long, state: Boolean) -> Unit = { _, _ -> },
    private val openTimePicker: () -> Unit = {}
): AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder {
        return SwitchWithTimeViewHolder(parent, checkSwitch, openTimePicker)
    }

    override fun isValidType(model: BaseModel): Boolean {
        return model is MenuSwitchWithTimePicker
    }
}