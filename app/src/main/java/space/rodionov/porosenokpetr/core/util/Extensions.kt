package space.rodionov.porosenokpetr.core.util

import android.app.Activity
import android.content.Intent
import android.util.Log
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE
import space.rodionov.porosenokpetr.core.util.Constants.WORD_LEARNED
import kotlin.math.roundToInt

inline fun <reified T: Activity> Activity.startActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
    finish()
}

fun List<Word>.countPercentage(): Int {
    val learnedCount = this.filter {
        it.wordStatus == WORD_LEARNED
    }.size
    val totalIncludedCount = this.filter {
        it.wordStatus == WORD_LEARNED || it.wordStatus == WORD_ACTIVE
    }.size
    Log.d("TAG_PERCENT", "countPercentage: learnedCount = $learnedCount")
    Log.d("TAG_PERCENT", "countPercentage: totalIncludedCount = $totalIncludedCount")
    val lch = learnedCount * 100.0f
    val percentage = if (!(lch / totalIncludedCount).isNaN()) (lch / totalIncludedCount).roundToInt() else 0
    Log.d("TAG_PERCENT", "countPercentage: $percentage%")
    return percentage
}







