package space.rodionov.porosenokpetr.feature_cardstack.presentation.componensts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.core.redrawViewGroup
import space.rodionov.porosenokpetr.databinding.ItemWordBinding
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

class CardStackAdapter(
    private val onSpeakWord: (String) -> Unit = {}
) : ListAdapter<CardStackItem.WordUi, CardStackAdapter.CardStackViewHolder>(CardStackItemComparator()) {

    inner class CardStackViewHolder(
        private val binding: ItemWordBinding,
        private val onSpeakItem: (Int) -> Unit = {}
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: CardStackItem.WordUi) {
            binding.apply {
                tvDowner.isVisible = false
                btnSpeak.isVisible = !word.isNativeToForeign

                tvUpper.text = word.getTranslation(3)
                tvDowner.text = word.getTranslation(0)

                updateNativeLang(word.nativeLang)
                updateIsNativeToForeign(word.isNativeToForeign)
                updateMode(word.mode)

                root.setOnClickListener {
                    tvDowner.isVisible = true
                    btnSpeak.isVisible = true
                }

                btnSpeak.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onSpeakItem(position)
                    }
                }
            }
        }

        fun bindPayload(word: CardStackItem.WordUi, payloads: List<Any>) {
            payloads.forEach { payload ->
                when (payload) {
                    Payloads.NATIVE_LANG -> updateNativeLang(word.nativeLang)
                    Payloads.IS_NATIVE_TO_FOREIGN -> updateIsNativeToForeign(word.isNativeToForeign)
                    Payloads.MODE -> updateMode(word.mode)
                }
            }
        }

        private fun updateNativeLang(nativeLang: Int) {
            //todo
        }

        private fun updateIsNativeToForeign(nativeToForeign: Boolean) {
            //todo
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
                if (word != null) onSpeakWord(word.getTranslation(3))
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
}