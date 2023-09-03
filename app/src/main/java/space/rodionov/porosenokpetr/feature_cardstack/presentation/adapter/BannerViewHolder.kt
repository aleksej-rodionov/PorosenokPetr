package space.rodionov.porosenokpetr.feature_cardstack.presentation.adapter

import android.view.ViewGroup
import space.rodionov.porosenokpetr.databinding.ItemBannerBinding
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

class BannerViewHolder(
    private val binding: ItemBannerBinding
) : BaseViewHolder(binding.root as ViewGroup) {

    override fun bind(item: CardStackItem, holder: BaseViewHolder) {
        item as CardStackItem.BannerUi
        binding.apply {
            tvBannerText.text = item.text
            ivBannerImage.setImageResource(item.imageRes)
        }
    }
}