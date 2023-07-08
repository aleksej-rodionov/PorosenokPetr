package space.rodionov.porosenokpetr.feature_cardstack.presentation.componensts

import androidx.recyclerview.widget.DiffUtil
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

class CardStackItemComparator : DiffUtil.ItemCallback<CardStackItem.WordUi>() {
    override fun areItemsTheSame(oldItem: CardStackItem.WordUi, newItem: CardStackItem.WordUi) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CardStackItem.WordUi, newItem: CardStackItem.WordUi) =
        oldItem.rus == newItem.rus && oldItem.ukr == newItem.ukr
                && oldItem.eng == newItem.eng && oldItem.categoryName == newItem.categoryName

    override fun getChangePayload(
        oldItem: CardStackItem.WordUi,
        newItem: CardStackItem.WordUi
    ): Any? {
        val payloads = mutableListOf<Any>()
        if (oldItem.nativeLang != newItem.nativeLang) {
            payloads.add(Payloads.NATIVE_LANG)
        }
        if (oldItem.isNativeToForeign != newItem.isNativeToForeign) {
            payloads.add(Payloads.IS_NATIVE_TO_FOREIGN_KEY)
        }
        if (oldItem.mode != newItem.mode) {
            payloads.add(Payloads.MODE)
        }
        return if (payloads.isEmpty()) null else payloads
    }
}

object Payloads {
    const val NATIVE_LANG = "nativeLang"
    const val IS_NATIVE_TO_FOREIGN_KEY = "isNativeToForeign"
    const val MODE = "mode"
}