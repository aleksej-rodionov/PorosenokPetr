package space.rodionov.porosenokpetr.feature_cardstack.presentation.componensts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.databinding.ItemWordBinding
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

class CardStackAdapter(
private val onSpeakWord: (String) -> Unit = {}
) : ListAdapter<CardStackItem.WordUi, CardStackAdapter.CardStackViewHolder>(WordDiff())/*, ModeForAdapter,
    LangForAdapter*/ {

    companion object {
        const val TAG_DRILLER_ADAPTER = "drillerAdapter"
    }

    //===================DARK MODE===========================
//    private var mode: Int = 0
//    override fun updateMode(newMode: Int) {
//        mode = newMode
//    }
//    override fun getTag(): String = TAG_DRILLER_ADAPTER
//
//    //===================LANG===========================
//    private var nativeLang: Int = LANGUAGE_RU
//    private var learnedLang: Int = LANGUAGE_EN
//    override fun updateNativeLang(newLang: Int) {
//        nativeLang = newLang
//    }
//    override fun updateLearnedLang(newLang: Int) {
//        learnedLang = newLang
//    }
//    override fun getTagForLang(): String = TAG_DRILLER_ADAPTER // todo попробовать просто getTag() назвать чтоб один на три интерфейса

    //=====================TRANSLATION DIR==========================
    private var mNativeToForeign: Boolean = false
    fun updateTransDir(nativeToForeign: Boolean) { mNativeToForeign = nativeToForeign }



    inner class CardStackViewHolder(
        private val binding: ItemWordBinding,
        private val onSpeakItem: (Int) -> Unit = {}
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: CardStackItem.WordUi) {
            binding.apply {
                tvDowner.isVisible = false
                btnSpeak.isVisible = !mNativeToForeign

                tvUpper.text = word.getTranslation(3)
                tvDowner.text = word.getTranslation(0)
//
//                (root as ViewGroup).redrawViewGroup(mode)

                root.setOnClickListener {
                    tvDowner.isVisible = true
                    btnSpeak.isVisible = true
                }

                btnSpeak.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onSpeakItem(position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardStackViewHolder(
            binding,
            onSpeakItem = { pos ->
                val word = getItem(pos)
                if (word != null) onSpeakWord(word.getTranslation(3))
            }
        )
    }

    override fun onBindViewHolder(holder: CardStackViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)
    }
}

class WordDiff : DiffUtil.ItemCallback<CardStackItem.WordUi>() {
    override fun areItemsTheSame(oldItem: CardStackItem.WordUi, newItem: CardStackItem.WordUi) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CardStackItem.WordUi, newItem: CardStackItem.WordUi) =
        oldItem.rus == newItem.rus && oldItem.ukr == newItem.ukr
                && oldItem.eng == newItem.eng && oldItem.categoryName == newItem.categoryName
}