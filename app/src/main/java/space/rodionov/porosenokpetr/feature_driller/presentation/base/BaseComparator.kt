package space.rodionov.porosenokpetr.feature_driller.presentation.base

import androidx.recyclerview.widget.DiffUtil
import space.rodionov.porosenokpetr.feature_settings.domain.model.BaseModel

class BaseComparator<T : BaseModel>: DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.isIdDiff(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.isContentDiff(newItem)
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? {
        return oldItem.getPayloadDiff(newItem)
    }
}