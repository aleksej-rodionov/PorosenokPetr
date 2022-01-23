package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.ItemWordCardBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.presentation.base.adapter.AdapterDelegate
import space.rodionov.porosenokpetr.feature_driller.presentation.base.adapter.BaseViewHolder
import space.rodionov.porosenokpetr.util.redrawViewGroup

//class DrillerViewHolder(parent: ViewGroup) :
//    BaseViewHolder(parent, R.layout.item_word_card) {
//
//    lateinit var binding: ItemWordCardBinding
//
//    fun bind(word: Word) {
//        binding = ItemWordCardBinding.bind(itemView)
//        binding.apply {
//            tvUpper.text = if(mNativeToForeign) word.nativ else word.foreign
//            tvDowner.text = if(mNativeToForeign) word.foreign else word.nativ
//
//            (binding.root as ViewGroup).redrawViewGroup(isNightBaseAdapter)
//        }
//    }
//}
//
//class DrillerDelegate: AdapterDelegate {
//
//    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder {
//        TODO("Not yet implemented")
//    }
//
//    override fun isValidType(model: BaseModel): Boolean = model is Word
//}