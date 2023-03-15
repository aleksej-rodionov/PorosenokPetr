package space.rodionov.porosenokpetr.feature_settings.domain.model

import space.rodionov.porosenokpetr.core.util.dpToPx
import space.rodionov.porosenokpetr.core.util.LocalizedString

class Header(
    val header: String? = null,
    val text: LocalizedString? = null,
//    val resId: Int? = null,
    val padding: Int = dpToPx(4f).toInt()
): BaseModel