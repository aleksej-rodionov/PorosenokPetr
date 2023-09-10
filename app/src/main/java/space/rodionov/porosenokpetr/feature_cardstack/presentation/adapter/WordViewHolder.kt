package space.rodionov.porosenokpetr.feature_cardstack.presentation.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.core.redrawViewGroup
import space.rodionov.porosenokpetr.databinding.ItemWordBinding
import space.rodionov.porosenokpetr.feature_cardstack.presentation.components.ExampleView
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem.WordUi.Companion.IS_NATIVE_TO_FOREIGN_PAYLOAD
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem.WordUi.Companion.MODE_PAYLOAD
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem.WordUi.Companion.NATIVE_LANG_PAYLOAD

class WordViewHolder(
    private val binding: ItemWordBinding,
    private val onSpeakWordClick: (String) -> Unit = {},
    private val onEditWordClick: (CardStackItem.WordUi) -> Unit = {}
) : BaseViewHolder(binding.root as ViewGroup) {

    override fun bind(item: CardStackItem, holder: BaseViewHolder) {
        binding.apply {
            item as CardStackItem.WordUi
            tvDowner.visibility = View.INVISIBLE
            btnSpeak.visibility = if (item.isNativeToForeign) View.GONE else View.VISIBLE
            divider.visibility = View.GONE
            llExamples.visibility = View.GONE

            updateTranslations(item)
            updateMode(item.mode)

            root.setOnClickListener {
                tvDowner.visibility = View.VISIBLE
                btnSpeak.visibility = View.VISIBLE
                if (item.examples.isNotEmpty()) {
                    divider.visibility = View.VISIBLE
                    llExamples.visibility = View.VISIBLE
                    val examples: List<View> = item.examples.map {
                        val exampleView = ExampleView(itemView.context).apply {
                            setText(it, item.mode)
                        }
                        return@map exampleView
                    }

                    llExamples.bindViews(*examples.toTypedArray())
                }
            }

            btnSpeak.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onSpeakWordClick(item.getTranslation(item.learnedLanguage))
                }
            }

            icEdit.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onEditWordClick(item)
                }
            }
        }
    }

    override fun bindPayload(
        item: CardStackItem,
        holder: BaseViewHolder,
        payloads: MutableList<Any>
    ) {
        item as CardStackItem.WordUi
        payloads.forEach { payload ->
            when (payload) {
                NATIVE_LANG_PAYLOAD -> updateTranslations(item)
                IS_NATIVE_TO_FOREIGN_PAYLOAD -> updateTranslations(item)
                MODE_PAYLOAD -> updateMode(item.mode)
            }
        }
    }

    private fun updateTranslations(word: CardStackItem.WordUi) {
        binding.tvUpper.text = if (word.isNativeToForeign) word.getTranslation(word.nativeLang)
        else word.getTranslation(word.learnedLanguage)

        binding.tvDowner.text =
            if (word.isNativeToForeign) word.getTranslation(word.learnedLanguage)
            else word.getTranslation(word.nativeLang)

        binding.tvUpper.alterSize()
        binding.tvDowner.alterSize()
    }

    private fun updateMode(mode: Int) {
        (binding.root as ViewGroup).redrawViewGroup(mode)
    }

    companion object {
        private fun ViewGroup.bindViews(
            vararg views: View
        ) {
            this.removeAllViews()
            views.forEach { view ->
                this.addView(view)
            }
        }
    }
}

fun TextView.alterSize() {
    this.post {
        when (this.layout?.lineCount) {
            1 -> this.textSize = 20f
            2 -> this.textSize = 18f
            3 -> this.textSize = 16f
            4 -> this.textSize = 14f
            else -> this.textSize = 12f
        }
    }
}