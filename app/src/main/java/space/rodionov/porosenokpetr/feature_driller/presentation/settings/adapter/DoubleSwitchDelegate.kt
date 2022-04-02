package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.fetchColors
import space.rodionov.porosenokpetr.databinding.ItemSettingsDoubleSwitchBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.presentation.base.AdapterDelegate
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseViewHolder
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_DARK
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

//class DoubleSwitchViewHolder(
//    val parent: ViewGroup,
//    val checkSwitch: (type: SettingsSwitchType, state: Boolean) -> Unit = { _, _ -> }
//): BaseViewHolder( parent, R.layout.item_settings_double_switch) {
//    lateinit var binding: ItemSettingsDoubleSwitchBinding
//    override fun bind(model: BaseModel, holder: BaseViewHolder) {
//        binding = ItemSettingsDoubleSwitchBinding.bind(itemView)
//        with(binding) {
//            model as MenuDoubleSwitch
//            switchViewFirst.setOnCheckedChangeListener(null)
//            switchViewSecond.setOnCheckedChangeListener(null)
//            model.descriptionFirstId?.let { descId ->
//                tvDescriptionFirst.visibility = View.VISIBLE
//                tvDescriptionFirst.text = res.getString(descId)
//            }
//            model.descriptionSecondId?.let { descId ->
//                tvDescriptionSecond.visibility = View.VISIBLE
//                tvDescriptionSecond.text = res.getString(descId)
//            }
//
////            bindSwitchState(model.typeFirst, switchViewFirst)
////            bindSwitchState(model.typeSecond, switchViewSecond)
//
//            switchViewFirst.setOnCheckedChangeListener { _, isChecked ->
//                checkSwitch(model.typeFirst, isChecked)
//            }
//            switchViewSecond.setOnCheckedChangeListener { _, isChecked ->
//                checkSwitch(model.typeSecond, isChecked)
//            }
//        }
//    }
//
////    private fun bindSwitchState(
////        type: SettingsSwitchType,
////        switch: SwitchCompat,
////    ) {
////        when(type) {
////            SettingsSwitchType.NIGHT_MODE -> {
////                switch.isChecked = modeBVH == MODE_DARK
////                if (followSystemModeBVH) {
////                    switch.setTextColor(res.getColor(R.color.gray600))
////                    switch.thumbTintList = ColorStateList.valueOf(res.getColor(R.color.gray600))
////                    switch.isEnabled = false
////                } else {
////                    switch.setTextColor(colors[3])
////                    switch.thumbTintList = ColorStateList.valueOf(res.getColor(R.color.white))
////                    switch.isEnabled = true
////                }
////            }
////            SettingsSwitchType.SYSTEM_MODE -> {
////                switch.isChecked = followSystemModeBVH
////            }
////        }
////    }
//}
//
//
//class DoubleSwitchDelegate(
//    private val checkSwitch: (type: SettingsSwitchType, state: Boolean) -> Unit = { _, _ -> }
//): AdapterDelegate {
//
//    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder {
//        return DoubleSwitchViewHolder(parent, checkSwitch)
//    }
//
//    override fun isValidType(model: BaseModel): Boolean {
//        return model is MenuDoubleSwitch
//    }
//}