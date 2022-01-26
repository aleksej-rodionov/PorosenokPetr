package space.rodionov.porosenokpetr.feature_driller.presentation.base

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class MinHeightRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0): RecyclerView(context, attrs, defStyleAttrs) {

    var rvHeight = 400

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val hSpec = MeasureSpec.makeMeasureSpec(rvHeight, MeasureSpec.AT_MOST)
        super.onMeasure(widthSpec, hSpec)
    }
}