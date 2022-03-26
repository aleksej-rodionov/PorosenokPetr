package space.rodionov.porosenokpetr.core

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

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

fun View.updateHorizontalMargin(margin: Int) {
    updateLayoutParams {
        this as RecyclerView.LayoutParams
        leftMargin = margin
        rightMargin = margin

    }
}