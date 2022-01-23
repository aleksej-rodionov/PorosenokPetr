package space.rodionov.porosenokpetr.feature_driller.presentation.base.adapter

import androidx.recyclerview.widget.DiffUtil
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel

class BaseDiffUtil<T: BaseModel>: DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) =
        oldItem.isIdDiff(newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T) =
        oldItem.isContentDiff(newItem)

    override fun getChangePayload(oldItem: T, newItem: T) =
        oldItem.getPayloadDiff(newItem)
}