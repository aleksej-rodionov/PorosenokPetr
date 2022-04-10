package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import android.view.ViewGroup
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.ItemSettingsItemBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.domain.models.MenuItem
import space.rodionov.porosenokpetr.feature_driller.domain.models.MenuSwitch
import space.rodionov.porosenokpetr.feature_driller.presentation.base.AdapterDelegate
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseViewHolder
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsItemType

class ItemViewHolder(
    val parent: ViewGroup,
    val clickItem: (type: SettingsItemType) -> Unit = {}
): BaseViewHolder(parent, R.layout.item_settings_item) {
    lateinit var binding: ItemSettingsItemBinding
    override fun bind(model: BaseModel, holder: BaseViewHolder) {
        binding = ItemSettingsItemBinding.bind(itemView)
        with(binding) {
            model as MenuItem
            text.text = "${res.getString(model.text.getIdByLang(nativeLangBVH))}" + ""

            text.setOnClickListener {
                clickItem(model.type)
            }
        }
    }
}

class ItemDelegate(
    private val onItemClick: (type: SettingsItemType) -> Unit = {}
): AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder {
        return ItemViewHolder(parent, onItemClick)
    }

    override fun isValidType(model: BaseModel): Boolean {
        return model is MenuItem
    }
}