package space.rodionov.porosenokpetr.feature_driller.domain.models

import space.rodionov.porosenokpetr.core.dpToPx

class Header(
    val header: String? = null,
    val resId: Int? = null,
    val padding: Int = dpToPx(4f).toInt()
): BaseModel