package space.rodionov.porosenokpetr.feature_driller.presentation.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.util.redrawViewGroup

abstract class BaseViewHolder: RecyclerView.ViewHolder {

    var isNightBaseViewHolder = false
        set(value) {
            field = value
//            theme = fetchTheme(value, itemView.resources)
//            colors = fetchTheme(value, itemView.resources).fetchColors()
        }
//    var theme = fetchTheme(isNightBaseViewHolder, itemView.resources)
//    var colors = theme.fetchColors()

    constructor(parent: ViewGroup, layoutId: Int):
            super(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    constructor(view: View): super(view)

    open fun bind(model: BaseModel, viewHolder: BaseViewHolder) {}

    open fun bindPayload(
        model: BaseModel,
        viewHolder: BaseViewHolder,
        payloads: MutableList<Any>
    ) {}

    open fun setMode(isNight: Boolean) {
        isNightBaseViewHolder = isNight

        if (itemView is ViewGroup) itemView.redrawViewGroup(isNight, false)
    }
}