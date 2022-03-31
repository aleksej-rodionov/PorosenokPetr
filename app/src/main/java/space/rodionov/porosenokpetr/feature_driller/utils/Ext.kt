package space.rodionov.porosenokpetr.feature_driller.utils

import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import kotlin.math.roundToInt


fun List<Word>.countPercentage(): Int {
    val learnedCount = this.filter {
        !it.isWordActive
    }.size
    val totalCount = this.size
    val lch = learnedCount * 100.0f
    val percentage = if (!(lch / totalCount).isNaN()) (lch / totalCount).roundToInt() else 0
    return percentage
}