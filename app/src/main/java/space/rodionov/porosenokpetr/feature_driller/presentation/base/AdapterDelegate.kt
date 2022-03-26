package space.rodionov.porosenokpetr.feature_driller.presentation.base

import android.view.ViewGroup
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel

interface AdapterDelegate {

    fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder

    fun isValidType(model: BaseModel): Boolean
}