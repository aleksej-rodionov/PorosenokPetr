package space.rodionov.porosenokpetr.feature_driller.presentation

import androidx.recyclerview.widget.DiffUtil
import space.rodionov.porosenokpetr.feature_driller.domain.models.CatWithWords
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.utils.countPercentage

class WordDiff : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Word, newItem: Word) =
        oldItem.rus == newItem.rus && oldItem.ukr == newItem.ukr
                && oldItem.eng == newItem.eng && oldItem.categoryName == newItem.categoryName
}


class CatDiff : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Category, newItem: Category) =
        oldItem.isCategoryActive == newItem.isCategoryActive && oldItem.resourceName == newItem.resourceName
                && oldItem.nameRus == newItem.nameRus && oldItem.nameEng == newItem.nameEng
}

class CatWithWordsDiff : DiffUtil.ItemCallback<CatWithWords>() {
    override fun areItemsTheSame(oldItem: CatWithWords, newItem: CatWithWords) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CatWithWords, newItem: CatWithWords) =
        oldItem.category.isCategoryActive == newItem.category.isCategoryActive &&
                oldItem.category.resourceName == newItem.category.resourceName &&
                oldItem.words.countPercentage() == newItem.words.countPercentage()
}