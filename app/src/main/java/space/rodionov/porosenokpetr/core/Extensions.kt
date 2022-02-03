package space.rodionov.porosenokpetr.core

import android.util.Log
import space.rodionov.porosenokpetr.Constants
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import java.math.BigDecimal

fun Float.roundToTwoDecimals(): Float {
    var bd = BigDecimal(java.lang.Float.toString(this))
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
    return bd.toFloat()
}

fun List<Word>.countPercentage(): Int {
    val learnedCount = this.filter {
        !it.isWordActive
    }.size
    val totalCount = this.size
//    val share = (learnedCount.toFloat() / totalCount.toFloat()).roundToTwoDecimals()
//    Log.d(Constants.TAG_PETR, "countPercentage: share = $share")
//    val percentage = share * 100
    val lch = learnedCount * 100
    val tch = totalCount * 100
    val percentage = lch / tch
    Log.d(Constants.TAG_PETR, "countPercentage: percentage = $percentage %")
    return percentage
}