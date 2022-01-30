package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.databinding.ItemWordCardBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.presentation.WordDiff
import space.rodionov.porosenokpetr.util.redrawViewGroup

class DrillerAdapter(
    private val onSpeakWord: (String) -> Unit = {}
) : ListAdapter<Word, DrillerAdapter.DrillerViewHolder>(WordDiff()){

    private var mIsNight: Boolean = false
    fun updateMode(isNight: Boolean) { mIsNight = isNight }

    private var mNativeToForeign: Boolean = false
    fun updateTransDir(nativeToForeign: Boolean) { mNativeToForeign = nativeToForeign }

    inner class DrillerViewHolder(
        private val binding: ItemWordCardBinding,
        private val onSpeakItem: (Int) -> Unit = {}
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.apply {
                tvDowner.isVisible = false
                btnSpeak.isVisible = !mNativeToForeign

                tvUpper.text = if(mNativeToForeign) word.nativ else word.foreign
                tvDowner.text = if(mNativeToForeign) word.foreign else word.nativ

                (root as ViewGroup).redrawViewGroup(mIsNight)

                root.setOnClickListener {
                    tvDowner.isVisible = true
                    btnSpeak.isVisible = true
                }

                btnSpeak.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) onSpeakItem(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrillerViewHolder {
        val binding = ItemWordCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrillerViewHolder(
            binding,
            onSpeakItem = { pos ->
                val word = getItem(pos)
                if (word != null) onSpeakWord(word.foreign)
            }
        )
    }

    override fun onBindViewHolder(holder: DrillerViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)
    }
}











