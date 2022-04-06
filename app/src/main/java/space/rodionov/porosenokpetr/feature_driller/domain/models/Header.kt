package space.rodionov.porosenokpetr.feature_driller.domain.models

import space.rodionov.porosenokpetr.core.dpToPx
import space.rodionov.porosenokpetr.feature_driller.utils.LocalizedString

class Header(
    val header: String? = null,
    val text: LocalizedString? = null,
//    val resId: Int? = null,
    val padding: Int = dpToPx(4f).toInt()
): BaseModel