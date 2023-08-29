package space.rodionov.porosenokpetr.feature_cardstack.presentation.componensts

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import space.rodionov.porosenokpetr.databinding.ViewExampleBinding

class ExampleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    attrStyle: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, attrStyle) {

    private val binding = ViewExampleBinding.inflate(
        LayoutInflater.from(context),
        this,
        false
    )

    fun setText(example: String) {
        binding.tvExample.text = example
        invalidate()
        requestLayout()
    }
}