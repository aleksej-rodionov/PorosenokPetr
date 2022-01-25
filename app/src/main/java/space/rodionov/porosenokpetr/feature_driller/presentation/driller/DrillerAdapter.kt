package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.databinding.ItemWordCardBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.presentation.WordDiff
import space.rodionov.porosenokpetr.util.redrawViewGroup

class DrillerAdapter() : ListAdapter<Word, DrillerAdapter.DrillerViewHolder>(WordDiff()){

    private var mIsNight: Boolean = false
    fun updateMode(isNight: Boolean) { mIsNight = isNight }

    private var mNativeToForeign: Boolean = false
    fun updateTransDir(nativeToForeign: Boolean) { mNativeToForeign = nativeToForeign }

    inner class DrillerViewHolder(private val binding: ItemWordCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrillerViewHolder {
        val binding = ItemWordCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrillerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrillerViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)
    }

    fun getWordAt(pos: Int) = getItem(pos)
}











