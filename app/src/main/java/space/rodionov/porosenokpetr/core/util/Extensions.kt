package space.rodionov.porosenokpetr.core.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE
import space.rodionov.porosenokpetr.core.util.Constants.WORD_LEARNED
import java.math.BigDecimal
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
    val lch = learnedCount * 100.0f
    val percentage = if (!(lch / totalIncludedCount).isNaN()) (lch / totalIncludedCount).roundToInt() else 0
    Log.d("TAG_PERCENT", "countPercentage: $percentage%")
    return percentage
}

fun Float.roundToTwoDecimals(): Float {
    var bd = BigDecimal(java.lang.Float.toString(this))
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
    return bd.toFloat()
}

fun AutoCompleteTextView.showKeyboard() {
    this.requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.vectorToBitmap(drawableId: Int): Bitmap? {
    val drawable = ContextCompat.getDrawable(this, drawableId) ?: return null
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    ) ?: return null
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun dpToPx(sp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        sp,
        Resources.getSystem().displayMetrics
    )
}

//fun List<BaseModel>.findMenuItem(type: SettingsSwitchType) : BaseModel? {
//    return this.findLast {
//        (it is MenuSwitch && it.type == type) || (it is MenuSwitchWithTimePicker && it.type == type)
//    }
//}









