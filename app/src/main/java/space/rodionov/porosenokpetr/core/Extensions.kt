package space.rodionov.porosenokpetr.core

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import space.rodionov.porosenokpetr.Constants
import space.rodionov.porosenokpetr.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import java.math.BigDecimal
import kotlin.math.roundToInt

fun Float.roundToTwoDecimals(): Float {
    var bd = BigDecimal(java.lang.Float.toString(this))
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
    return bd.toFloat()
}

//fun Float.roundToInt(): Float {
//    var bd = BigDecimal(java.lang.Float.toString(this))
//    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
//    return bd.toFloat()
//}

fun List<Word>.countPercentage(): Int {
    val learnedCount = this.filter {
        !it.isWordActive
    }.size
    val totalCount = this.size
    val lch = learnedCount * 100.0f
    val percentage = (lch / totalCount).roundToInt()
    return percentage
}

fun AutoCompleteTextView.showKeyboard() {
    this.requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}