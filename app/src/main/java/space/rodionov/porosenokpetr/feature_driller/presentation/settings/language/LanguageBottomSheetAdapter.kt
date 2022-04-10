package space.rodionov.porosenokpetr.feature_driller.presentation.settings.language

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.databinding.ItemLanguageBinding
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_RU
import space.rodionov.porosenokpetr.feature_driller.utils.LangForAdapter

class LanguageAdapter(
    private val onClickLang: (Int) -> Unit = {}
): ListAdapter<LanguageItem, LanguageAdapter.LanguageViewHolder>(LanguageComparator()), LangForAdapter {

    companion object {
        const val TAG_LANGUAGE_ADAPTER = "languageAdapter"
    }

    private var nativeLang = NATIVE_LANGUAGE_RU
    override fun updateNativeLang(newLang: Int) { nativeLang = newLang }
    override fun updateLearnedLang(newLang: Int) { /*empty*/ }
    override fun getTagForLang(): String = TAG_LANGUAGE_ADAPTER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding = ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(
            binding,
            onClickLang = { langIndex ->
                onClickLang(langIndex)
            }
        )
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)
    }

    inner class LanguageViewHolder(
        val binding: ItemLanguageBinding,
        val onClickLang: (Int) -> Unit = {}
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(lang: LanguageItem) {
            with(binding) {
                tvText.text = lang.getLocalizedName(nativeLang)
                ivCheck.visibility = if (lang.langIndex == nativeLang) View.VISIBLE
                    else View.INVISIBLE

                root.setOnClickListener {
                    onClickLang(lang.langIndex)
                }
            }
        }
    }
}

class LanguageComparator: DiffUtil.ItemCallback<LanguageItem>() {
    override fun areItemsTheSame(oldItem: LanguageItem, newItem: LanguageItem): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: LanguageItem, newItem: LanguageItem): Boolean {
        return oldItem.langIndex==newItem.langIndex && oldItem.nameRus==newItem.nameRus &&
                oldItem.nameEng==newItem.nameEng
    }
}