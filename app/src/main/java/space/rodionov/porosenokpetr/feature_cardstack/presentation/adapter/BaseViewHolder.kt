package space.rodionov.porosenokpetr.feature_cardstack.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

abstract class BaseViewHolder(
    viewGroup: ViewGroup
) : RecyclerView.ViewHolder(viewGroup) {

    /**
     * Выполняет стандартное отображение данных во вью
     */
    open fun bind(item: CardStackItem, holder: BaseViewHolder) {}

    /**
     * Выборочно перерисовывает только те элементы вью, которых коснулись изменения
     */
    open fun bindPayload(
        item: CardStackItem,
        holder: BaseViewHolder,
        payloads: MutableList<Any>
    ) {}
}