package space.rodionov.porosenokpetr.feature_driller.presentation.base

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.core.fetchColors
import space.rodionov.porosenokpetr.core.fetchTheme
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_RU
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_NATIVE_LANG
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_SETTINGS

abstract class BaseViewHolder: RecyclerView.ViewHolder {

    var modeBVH: Int = MODE_LIGHT
        set(value) {
            mode = fetchTheme(value, res)
            colors = fetchColors(value, res)
        }

    var mode = fetchTheme(modeBVH, itemView.resources)
    var colors = mode.fetchColors()

    var nativeLangBVH: Int = NATIVE_LANGUAGE_RU

    constructor(parent: ViewGroup, layoutId: Int):
            super(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    constructor(view: View): super(view)

    open fun bind(model: BaseModel, holder: BaseViewHolder) {}

    open fun bindPayload(
        model: BaseModel,
        holder: BaseViewHolder,
        payloads: MutableList<Any>
    ) {}

    val res: Resources = itemView.context.resources

    //========================SETTER METHODS====================

    fun setMode(mode: Int) {
        modeBVH = mode
    }

    fun setNativeLang(lang: Int) {
        nativeLangBVH = lang
//        Log.d(TAG_NATIVE_LANG, "setNativeLang in BVH: $lang")
    }
}