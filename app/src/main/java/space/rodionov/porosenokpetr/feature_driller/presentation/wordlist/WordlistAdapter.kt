package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.ItemWordHorizontalBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.presentation.WordDiff

class WordlistAdapter(
    private val onClickLearned: (Word)-> Unit = {},
    private val onSpeakWord: (String) -> Unit = {}
) : ListAdapter<Word, WordlistAdapter.WordlistViewHolder>(WordDiff()) {

    inner class WordlistViewHolder(
        private val binding: ItemWordHorizontalBinding,
        private val onLearnedItem: (Int) -> Unit = {},
        private val onSpeakItem: (Int) -> Unit = {}
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.apply {
                tvUpper.text = word.foreign
                tvDowner.text = word.nativ
                tvCategory.text = word.categoryName

                if (word.isWordActive) {
                    ivLearned.setImageDrawable(root.context.getDrawable(R.drawable.ic_new_round))
                } else {
                    ivLearned.setImageDrawable(root.context.getDrawable(R.drawable.ic_learned))
                }

                ivLearned.setOnClickListener {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) onLearnedItem(pos)
                }
                ivSpeak.setOnClickListener {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) onSpeakItem(pos)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordlistViewHolder {
        val binding = ItemWordHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordlistViewHolder(
            binding,
            onLearnedItem = { pos ->
                val word = getItem(pos)
                word?.let { onClickLearned(it) }
            },
            onSpeakItem = { pos ->
                val word = getItem(pos)
                word?.let { onSpeakWord(it.foreign) }
            }
        )
    }

    override fun onBindViewHolder(holder: WordlistViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)
    }
}