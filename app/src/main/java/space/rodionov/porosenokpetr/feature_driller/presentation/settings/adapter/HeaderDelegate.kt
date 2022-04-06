package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import android.view.ViewGroup
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.ItemHeaderBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.domain.models.Header
import space.rodionov.porosenokpetr.feature_driller.presentation.base.AdapterDelegate
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseViewHolder
import java.util.*

class HeaderViewHolder(parent: ViewGroup) : BaseViewHolder(parent, R.layout.item_header) {
    lateinit var binding: ItemHeaderBinding
    override fun bind(model: BaseModel, holder: BaseViewHolder) {
        binding = ItemHeaderBinding.bind(itemView)
        with(binding) {
            model as Header
            model.text?.let {
                tvTitle.text = res.getString(if (nativeLangBVH==1) it.uaId else it.ruId)
            } ?: tvTitle.setText(model.header?.let {
                it.substring(0, 1).toUpperCase(Locale.getDefault()) + it.substring(1)
            })
        }
    }
}

class HeaderDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder {
        return HeaderViewHolder(parent)
    }

    override fun isValidType(model: BaseModel): Boolean {
        return model is Header
    }
}