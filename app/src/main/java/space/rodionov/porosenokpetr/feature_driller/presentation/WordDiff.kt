package space.rodionov.porosenokpetr.feature_driller.presentation

import androidx.recyclerview.widget.DiffUtil
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word

class WordDiff : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Word, newItem: Word) =
        oldItem.foreign == newItem.foreign && oldItem.nativ == newItem.nativ
                && oldItem.categoryName == newItem.categoryName
}


class CatDiff : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Category, newItem: Category) =
        oldItem.isCategoryActive == newItem.isCategoryActive && oldItem.name == newItem.name
}