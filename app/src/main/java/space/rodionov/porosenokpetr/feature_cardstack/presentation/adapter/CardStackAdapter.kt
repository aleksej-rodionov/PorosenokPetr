package space.rodionov.porosenokpetr.feature_cardstack.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import space.rodionov.porosenokpetr.databinding.ItemBannerBinding
import space.rodionov.porosenokpetr.databinding.ItemWordBinding
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem.Companion.VIEW_TYPE_BANNER
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem.Companion.VIEW_TYPE_WORD

class CardStackAdapter(
    private val onSpeakWordClick: (String) -> Unit = {},
    private val onEditWordClick: (CardStackItem.WordUi) -> Unit = {}
) : ListAdapter<CardStackItem, BaseViewHolder>(CardStackItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_WORD -> {
                val binding = ItemWordBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                WordViewHolder(binding, onSpeakWordClick, onEditWordClick)
            }

            VIEW_TYPE_BANNER -> {
                val binding = ItemBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                BannerViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position), holder)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bindPayload(getItem(position), holder, payloads[0] as MutableList<Any>)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }
}