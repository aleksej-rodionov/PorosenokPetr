package space.rodionov.porosenokpetr.feature_cardstack.presentation.componensts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.core.redrawViewGroup
import space.rodionov.porosenokpetr.databinding.ItemWordBinding
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

class CardStackAdapter(
    private val onSpeakWordClick: (String) -> Unit = {},
    private val onEditWordClick: (CardStackItem.WordUi) -> Unit = {}
) : ListAdapter<CardStackItem.WordUi, CardStackAdapter.CardStackViewHolder>(CardStackItemComparator()) {

    inner class CardStackViewHolder(
        private val binding: ItemWordBinding,
        private val onSpeakItem: (Int) -> Unit = {},
        private val onClickEdit: (Int) -> Unit = {}
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: CardStackItem.WordUi) {
            binding.apply {
                tvDowner.visibility = View.INVISIBLE
                btnSpeak.visibility = if (word.isNativeToForeign) View.GONE else View.VISIBLE
                divider.visibility = View.GONE
                llExamples.visibility = View.GONE

                updateTranslations(word)
                updateMode(word.mode)

                root.setOnClickListener {
                    tvDowner.visibility = View.VISIBLE
                    btnSpeak.visibility = View.VISIBLE
                    if (word.examples.isNotEmpty()) {
                        divider.visibility = View.VISIBLE
                        llExamples.visibility = View.VISIBLE
                        val examples: List<View> = word.examples.map {
                            val exampleView = ExampleView(itemView.context).apply {
                                setText(it, word.mode)
                            }
                            return@map exampleView
                        }

                        llExamples.bindViews(*examples.toTypedArray())
                    }
                }

                btnSpeak.setOnClickListener {
                    val position = absoluteAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onSpeakItem(position)
                    }
                }

                icEdit.setOnClickListener {
                    val position = absoluteAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onClickEdit(position)
                    }
                }
            }
        }

        fun bindPayload(word: CardStackItem.WordUi, payloads: List<Any>) {
            payloads.forEach { payload ->
                when (payload) {
                    Payloads.NATIVE_LANG -> updateTranslations(word)
                    Payloads.IS_NATIVE_TO_FOREIGN -> updateTranslations(word)
                    Payloads.MODE -> updateMode(word.mode)
                }
            }
        }

        private fun updateTranslations(word: CardStackItem.WordUi) {
            binding.tvUpper.text = if (word.isNativeToForeign) word.getTranslation(word.nativeLang)
            else word.getTranslation(word.learnedLanguage)

            binding.tvDowner.text =
                if (word.isNativeToForeign) word.getTranslation(word.learnedLanguage)
                else word.getTranslation(word.nativeLang)
        }

        private fun updateMode(mode: Int) {
            (binding.root as ViewGroup).redrawViewGroup(mode)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardStackViewHolder(
            binding,
            onSpeakItem = { pos ->
                val word = getItem(pos)
                if (word != null) onSpeakWordClick(word.getTranslation(word.learnedLanguage))
            },
            onClickEdit = {
                val word = getItem(it)
                word?.let { onEditWordClick(word) }
            }
        )
    }

    override fun onBindViewHolder(holder: CardStackViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)
    }

    override fun onBindViewHolder(
        holder: CardStackViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val word = getItem(position)
            holder.bindPayload(word, payloads)
        }
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