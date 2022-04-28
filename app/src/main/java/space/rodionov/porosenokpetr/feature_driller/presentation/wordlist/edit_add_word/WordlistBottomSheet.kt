package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.edit_add_word

import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_PETR
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.fetchColors
import space.rodionov.porosenokpetr.core.redrawViewGroup
import space.rodionov.porosenokpetr.databinding.BottomsheetWordlistBinding
import space.rodionov.porosenokpetr.feature_driller.utils.LocalizationHelper

@AndroidEntryPoint
class WordlistBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val WORDLIST_BOTTOM_SHEET = "wordlistBottomSheet"
    }

    private val binding: BottomsheetWordlistBinding by lazy {
        BottomsheetWordlistBinding.inflate(layoutInflater)
    }
    private val vmEditAddWord: EditAddWordViewModel by viewModels()

    override fun getTheme(): Int = vmEditAddWord.mode.value?.let {
        when (it) {
            Constants.MODE_LIGHT -> R.style.Theme_NavBarDay
            Constants.MODE_DARK -> R.style.Theme_NavBarNight
            else -> R.style.Theme_NavBarDay
        }
    } ?: R.style.Theme_NavBarDay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        args?.let {
            Log.d(TAG_PETR, "onCreate: args NOT null")
            val rus = it.getString("rus")
            val eng = it.getString("eng")
            val categoryName = it.getString("categoryName")
            vmEditAddWord.nativLivedata.value = rus
            vmEditAddWord.foreignLivedata.value = eng
            vmEditAddWord.catNameLivedata.value = categoryName
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
        (view.parent as View).setBackgroundColor(Color.TRANSPARENT)

        binding.apply {
            this@WordlistBottomSheet.lifecycleScope.launchWhenStarted {
                vmEditAddWord.word.collectLatest {
                    it?.let {w->
                        Log.d(TAG_PETR, "observeWord BottomSheet New value = $w")
                    }
                    val word = it?: return@collectLatest

                    val natLang = vmEditAddWord.nativeLang.value

//                    tvWord.text = resources.getString(
//                        R.string.word_in_dialog,
//                        word.getTranslation(vmWordlist.learnedLang.value),
//                        word.getTranslation(natLang))
                    tvCategory.text = word.categoryName
                    val learned =
                        if (word.isWordActive) getString(LocalizationHelper.wordLearned.getIdByLang(natLang))
                        else getString(LocalizationHelper.wordNotLearned.getIdByLang(natLang))
//                    tvDecription.text = resources.getString(
//                        LocalizationHelper.wordDescription.getIdByLang(natLang),
//                        learned
//                    )
                    if (word.isWordActive) {
                        ivLearned.setImageDrawable(resources.getDrawable(R.drawable.ic_new_round))
                        ivLearned.imageTintList = null
                        ivLearned.imageTintList = ColorStateList.valueOf(fetchColors(vmEditAddWord.mode.value, resources)[3])
                    } else {
                        ivLearned.setImageDrawable(resources.getDrawable(R.drawable.ic_learned))
                        ivLearned.imageTintList = null
                        ivLearned.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.green))
                    }
                    switchLearned.setOnCheckedChangeListener(null)
                    switchLearned.isChecked = !word.isWordActive
                    switchLearned.text = learned
                    switchLearned.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) vmEditAddWord.inactivateWord() else vmEditAddWord.activateWord()
                    }
                }
            }

            this@WordlistBottomSheet.lifecycleScope.launchWhenStarted {
                vmEditAddWord.mode.collectLatest {
                    val mode = it ?: return@collectLatest
                    (root as ViewGroup).redrawViewGroup(mode)
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        vmEditAddWord.nativLivedata.value = null
        vmEditAddWord.foreignLivedata.value = null
        vmEditAddWord.catNameLivedata.value = null
    }
}





