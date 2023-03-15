package space.rodionov.porosenokpetr.feature_driller.presentation.settings.language

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.databinding.ItemLanguageBinding
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.core.util.Constants.NATIVE_LANGUAGE_CHANGE
import space.rodionov.porosenokpetr.core.util.LangForAdapter

class LanguageAdapter(
    private val onClickLang: (Int) -> Unit = {}
): ListAdapter<LanguageItem, LanguageAdapter.LanguageViewHolder>(LanguageComparator()),
    LangForAdapter {

    companion object {
        const val TAG_LANGUAGE_ADAPTER = "languageAdapter"
    }

    private var nativeOrForeign = NATIVE_LANGUAGE_CHANGE
    fun changeNativeOrForeign(natOrFor: Int) { nativeOrForeign = natOrFor }

    private var nativeLang = LANGUAGE_RU
    override fun updateNativeLang(newLang: Int) { nativeLang = newLang }
    private var learnedLang = LANGUAGE_EN
    override fun updateLearnedLang(newLang: Int) { learnedLang = newLang }
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
                if (nativeOrForeign == NATIVE_LANGUAGE_CHANGE) {
                    ivCheck.visibility = if (lang.langIndex == nativeLang) View.VISIBLE
                    else View.INVISIBLE
                } else {
                    ivCheck.visibility = if (lang.langIndex == learnedLang) View.VISIBLE
                    else View.INVISIBLE
                }

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