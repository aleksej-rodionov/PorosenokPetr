package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.databinding.ItemWordCardBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.presentation.WordDiff

class DrillerAdapter(

) : ListAdapter<Word, DrillerAdapter.DrillerViewHolder>(WordDiff()){

    private var mNativeToForeign: Boolean = false
    private var mIsNight: Boolean = false
    fun updateTransDir(nativeToForeign: Boolean) { mNativeToForeign = nativeToForeign }
    fun updateMode(isNight: Boolean) { mIsNight = isNight }

    inner class DrillerViewHolder(private val binding: ItemWordCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.apply {
                tvUpper.text = if(mNativeToForeign) word.nativ else word.foreign
                tvDowner.text = if(mNativeToForeign) word.foreign else word.nativ


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
}











