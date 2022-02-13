package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.Constants.TAG_PETR
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        args?.let {
            Log.d(TAG_PETR, "onCreate: args NOT null")
            val nativ = it.getString("nativ")
            val foreign = it.getString("foreign")
            val categoryName = it.getString("categoryName")
            vmWordlist.nativLivedata.value = nativ
            vmWordlist.foreignLivedata.value = foreign
            vmWordlist.catNameLivedata.value = categoryName
        }
    }


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
            this@WordlistBottomSheet.lifecycleScope.launchWhenStarted {
                vmWordlist.word.collectLatest {
                    it?.let {w->
                        Log.d(TAG_PETR, "observeWord BottomSheet New value = $w")
                    }
                    val word = it?: return@collectLatest
                    tvWord.text = resources.getString(R.string.word_in_dialog, word.foreign, word.nativ)
                    val learned =
                        if (word.isWordActive) getString(R.string.word_not_learned) else getString(R.string.word_learned)
                    tvDecription.text = resources.getString(R.string.word_description, learned)
                    if (word.isWordActive) {
                        ivLearned.setImageDrawable(resources.getDrawable(R.drawable.ic_new_round))
                    } else {
                        ivLearned.setImageDrawable(resources.getDrawable(R.drawable.ic_learned))
                    }
                    switchLearned.setOnCheckedChangeListener(null)
                    switchLearned.isChecked = !word.isWordActive
                    switchLearned.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) vmWordlist.inactivateWord() else vmWordlist.activateWord()
                    }
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
//        vmWordlist.wordInDialog.value = null
        vmWordlist.nativLivedata.value = null
        vmWordlist.foreignLivedata.value = null
        vmWordlist.catNameLivedata.value = null
    }
}





