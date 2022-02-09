package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.BottomsheetWordlistBinding
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class WordlistBottomSheet : BaseBottomSheetDialogFragment() {

    companion object {
        const val WORDLIST_BOTTOM_SHEET = "wordlistBottomSheet"
    }

    private val binding: BottomsheetWordlistBinding by lazy {
        BottomsheetWordlistBinding.inflate(layoutInflater)
    }
    private val vmWordlist: WordlistViewModel by viewModels({
        requireParentFragment()
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initModeObserver(binding.root, viewLifecycleOwner.lifecycleScope)

        binding.apply {
            // todo переделать это не с переменной во вьюмодели, а прям наблюдать состояние слова потоком из базы.
            val word = vmWordlist.wordInDialog
            word?.let {
                tvWord.text = resources.getString(R.string.word_in_dialog, it.foreign, it.nativ)
                val learned =
                    if (it.isWordActive) getString(R.string.word_not_learned) else getString(R.string.word_learned)
                tvDecription.text = resources.getString(R.string.word_description, learned)
                switchLearned.isChecked = !it.isWordActive
                if (it.isWordActive) {
                    ivLearned.setImageDrawable(resources.getDrawable(R.drawable.ic_new_round))
                } else {
                    ivLearned.setImageDrawable(resources.getDrawable(R.drawable.ic_learned))
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        vmWordlist.wordInDialog = null
    }
}





