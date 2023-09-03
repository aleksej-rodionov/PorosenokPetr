package space.rodionov.porosenokpetr.feature_cardstack.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

class CardStackItemComparator : DiffUtil.ItemCallback<CardStackItem>() {

    override fun areItemsTheSame(oldItem: CardStackItem, newItem: CardStackItem): Boolean {
        return oldItem.viewType == newItem.viewType && oldItem.isItemEqual(newItem)
    }

    override fun areContentsTheSame(oldItem: CardStackItem, newItem: CardStackItem): Boolean {
        return oldItem.isContentEqual(newItem)
    }

    override fun getChangePayload(oldItem: CardStackItem, newItem: CardStackItem): Any {
        return oldItem.getPayloadDiff(newItem)
    }
}