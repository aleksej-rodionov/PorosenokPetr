package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.edit_add_word

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.core.PorosenokPetrApp
import space.rodionov.porosenokpetr.core.util.Constants
import space.rodionov.porosenokpetr.core.util.Constants.TAG_PETR
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.util.GenericSavedStateViewModelFactory
import space.rodionov.porosenokpetr.core.util.redrawViewGroup
import space.rodionov.porosenokpetr.databinding.BottomsheetWordlistBinding
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.util.LocalizationHelper
import javax.inject.Inject

class WordlistBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val WORDLIST_BOTTOM_SHEET = "wordlistBottomSheet"
    }

    private val binding: BottomsheetWordlistBinding by lazy {
        BottomsheetWordlistBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var assistedFactory: EditAddWordViewModelFactory
    private val vmEditAddWord: EditAddWordViewModel by viewModels {
        GenericSavedStateViewModelFactory(assistedFactory, this)
    }

    override fun getTheme(): Int = vmEditAddWord.mode.value.let {
        when (it) {
            Constants.MODE_LIGHT -> R.style.Theme_NavBarDay
            Constants.MODE_DARK -> R.style.Theme_NavBarNight
            else -> R.style.Theme_NavBarDay
        }
    } ?: R.style.Theme_NavBarDay

    override fun onCreate(savedInstanceState: Bundle?) {
        PorosenokPetrApp.component?.inject(this)
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

            ivDone.setOnClickListener {
                var newWord: Word? = null
                if (BuildConfig.FLAVOR == "englishdriller") {
                   vmEditAddWord.word.value?.let {
                       newWord = it.copy(
                           rus = etRus.text.toString(),
                           eng = etForeign.text.toString()
                       )
                       newWord?.let { new ->
                           vmEditAddWord.onDoneCLick(new)
                       }
                   }
                }
                if (BuildConfig.FLAVOR == "swedishdriller") {
                    vmEditAddWord.word.value?.let {
                        newWord = it.copy(
                            rus = etRus.text.toString(),
                            ukr = etUkr.text.toString(),
                            eng = etEng.text.toString(),
                            swe = etForeign.text.toString()
                        )
                        newWord?.let { new ->
                            vmEditAddWord.onDoneCLick(new)
                        }
                    }
                }
                dismiss()
            }

            this@WordlistBottomSheet.lifecycleScope.launchWhenStarted {
                vmEditAddWord.word.collectLatest {
                    it?.let {w->
                        Log.d(TAG_PETR, "observeWord BottomSheet New value = $w")
                    }
                    val word = it?: return@collectLatest

                    val natLang = vmEditAddWord.nativeLang.value


                    // todo сетить переводы в эдиттексты в зависимости от Flavour-a
                    if (BuildConfig.FLAVOR == "englishdriller") {
                        etForeign.setHint(LocalizationHelper.englishTranslation.getIdByLang(natLang))
                        etRus.setHint(LocalizationHelper.russianTranslation.getIdByLang(natLang))
                        etForeign.setText(it.eng)
                        etRus.setText(it.rus)
                        groupEng.visibility = View.GONE
                        groupUkr.visibility = View.GONE
                    }
                    if (BuildConfig.FLAVOR == "swedishdriller") {
                        etForeign.setHint(LocalizationHelper.swedishWord.getIdByLang(natLang))
                        etRus.setHint(LocalizationHelper.russianTranslation.getIdByLang(natLang))
                        etEng.setHint(LocalizationHelper.englishTranslation.getIdByLang(natLang))
                        etUkr.setHint(LocalizationHelper.ukrainianTranslation.getIdByLang(natLang))

                        etForeign.setText(it.swe)
                        etRus.setText(it.rus)
                        etEng.setText(it.eng)
                        etUkr.setText(it.ukr)
                    }


                    tvCategory.text = getString(LocalizationHelper.category.getIdByLang(natLang))

                    if (chipGroupCategories.children.filter { chip ->
                            chip is Chip && chip.text == it.categoryName
                        }.toMutableList().isEmpty()) {
                        val chip = Chip(requireContext())
                        chip.text = it.categoryName
                        chipGroupCategories.addView(chip)
                    }

                    switchLearned.setOnCheckedChangeListener(null)
                    switchLearned.isChecked = !word.isWordActive

                    val learned =
                        if (word.isWordActive) getString(LocalizationHelper.wordLearned.getIdByLang(natLang))
                        else getString(LocalizationHelper.wordNotLearned.getIdByLang(natLang))
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





