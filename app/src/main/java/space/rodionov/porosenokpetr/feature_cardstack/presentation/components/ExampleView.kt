package space.rodionov.porosenokpetr.feature_cardstack.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import space.rodionov.porosenokpetr.core.redrawViewGroup
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
        true
    )

    fun setText(example: String, mode: Int) {
        binding.apply {
            tvExample.text = example
            root.redrawViewGroup(mode)
        }
    }
}