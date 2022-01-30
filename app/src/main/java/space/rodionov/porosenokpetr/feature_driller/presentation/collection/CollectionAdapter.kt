package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.databinding.ItemCategoryBinding
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.presentation.CatDiff

class CollectionAdapter(
    private val onSwitchCatActive: (Category, Boolean) -> Unit = { _, _ -> },
    private val onClickCat: (Category) -> Unit = { _ -> }
) : ListAdapter<Category, CollectionAdapter.CollectionViewHolder>(CatDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionViewHolder(
            binding,
            onCheckSwitch = { pos, isChecked ->
                val cat = getItem(pos)
                if (cat != null) onSwitchCatActive(cat, isChecked)
            },
            onClickItem = { pos ->
                val cat = getItem(pos)
                if (cat != null) onClickCat(cat)
            }
        )
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)
    }

    inner class CollectionViewHolder(
        private val binding: ItemCategoryBinding,
        private val onCheckSwitch: (Int, Boolean) -> Unit = { _, _ -> },
        private val onClickItem: (Int) -> Unit = {}
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.apply {
                tvCatName.text = category.name
                switchCat.setOnCheckedChangeListener(null)
                switchCat.isChecked = category.isCategoryActive
                switchCat.setOnCheckedChangeListener { switch, isChecked ->
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        onCheckSwitch(pos, isChecked)
                    }
                }
                root.setOnClickListener {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        onClickItem(pos)
                    }
                }
            }
        }
    }
}



