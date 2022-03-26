package space.rodionov.porosenokpetr.feature_driller.presentation.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.utils.Constants

abstract class BaseViewHolder: RecyclerView.ViewHolder {

    var translationDirectionBVH = false
    //todo night mode
    var notifyBVH: Boolean = false
    var notificationTimeBVH: Long = Constants.MILLIS_IN_NINE_HOURS


    constructor(parent: ViewGroup, layoutId: Int):
            super(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    constructor(view: View): super(view)

    open fun bind(model: BaseModel, holder: BaseViewHolder) {}

    open fun bindPayload(
        model: BaseModel,
        holder: BaseViewHolder,
        payloads: MutableList<Any>
    ) {}

    val res = itemView.context.resources

    //========================methods====================
    fun setTranslationDirection(nativeToForeign: Boolean) {
        translationDirectionBVH = nativeToForeign
    }
    //todo mode switch fuctionality
    fun setNotify(notify: Boolean) {
        notifyBVH = notify
    }
    fun setNotificationTime(millis: Long) {
        notificationTimeBVH = millis
    }
}