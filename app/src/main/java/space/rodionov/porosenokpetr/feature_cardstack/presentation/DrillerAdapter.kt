package space.rodionov.porosenokpetr.feature_cardstack.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.databinding.ItemWordCardBinding
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.feature_driller.presentation.WordDiff
import space.rodionov.porosenokpetr.core.util.ModeForAdapter
import space.rodionov.porosenokpetr.core.util.redrawViewGroup
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.core.util.LangForAdapter

class DrillerAdapter(
    private val onSpeakWord: (String) -> Unit = {}
) : ListAdapter<Word, DrillerAdapter.DrillerViewHolder>(WordDiff()), ModeForAdapter,
    LangForAdapter {

    companion object {
        const val TAG_DRILLER_ADAPTER = "drillerAdapter"
    }

    //===================DARK MODE===========================
    private var mode: Int = 0
    override fun updateMode(newMode: Int) {
        mode = newMode
    }
    override fun getTag(): String = TAG_DRILLER_ADAPTER

    //===================LANG===========================
    private var nativeLang: Int = LANGUAGE_RU
    private var learnedLang: Int = LANGUAGE_EN
    override fun updateNativeLang(newLang: Int) {
        nativeLang = newLang
    }
    override fun updateLearnedLang(newLang: Int) {
        learnedLang = newLang
    }
    override fun getTagForLang(): String = TAG_DRILLER_ADAPTER // todo попробовать просто getTag() назвать чтоб один на три интерфейса

    //=====================TRANSLATION DIR==========================
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

                tvUpper.text = if(mNativeToForeign) word.getTranslation(nativeLang) else word.getTranslation(learnedLang)
                tvDowner.text = if(mNativeToForeign) word.getTranslation(learnedLang) else word.getTranslation(nativeLang)

                (root as ViewGroup).redrawViewGroup(mode)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrillerViewHolder {
        val binding = ItemWordCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrillerViewHolder(
            binding,
            onSpeakItem = { pos ->
                val word = getItem(pos)
                if (word != null) onSpeakWord(word.getTranslation(learnedLang))
            }
        )
    }

    override fun onBindViewHolder(holder: DrillerViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)
    }
}











