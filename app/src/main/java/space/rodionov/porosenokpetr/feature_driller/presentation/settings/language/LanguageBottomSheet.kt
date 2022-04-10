package space.rodionov.porosenokpetr.feature_driller.presentation.settings.language

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.redrawViewGroup
import space.rodionov.porosenokpetr.databinding.BottomsheetLanguageBinding
import space.rodionov.porosenokpetr.feature_driller.utils.AppFlavor
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_CHANGE
import space.rodionov.porosenokpetr.feature_driller.utils.LocalizationHelper

@AndroidEntryPoint
class LanguageBottomSheet: BottomSheetDialogFragment() {

    companion object {
        const val LANGUAGE_BOTTOM_SHEET = "languageBottomSheet"
    }

    private val binding: BottomsheetLanguageBinding by lazy {
        BottomsheetLanguageBinding.inflate(layoutInflater)
    }

    private val vmLanguageSheet: LanguageBottomsheetViewModel by viewModels()

    private val langAdapter: LanguageAdapter by lazy {
        LanguageAdapter(
            onClickLang = {
                vmLanguageSheet.onLangChecked(it)
            }
        )
    }

    override fun getTheme(): Int = vmLanguageSheet.mode.value.let {
        when (it) {
            Constants.MODE_LIGHT -> R.style.Theme_NavBarDay
            Constants.MODE_DARK -> R.style.Theme_NavBarNight
            else -> R.style.Theme_NavBarDay
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        args?.let {
            vmLanguageSheet.nativeOrForeign.value = it.getInt("nativeForeign")
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

        if (BuildConfig.FLAVOR == "englishdriller") {
            vmLanguageSheet.inflateLanguageList(AppFlavor.ENGLISH_DRILLER)
        } else if (BuildConfig.FLAVOR == "swedishdriller") {
            vmLanguageSheet.inflateLanguageList(AppFlavor.SWEDISH_DRILLER)
        }

        binding.apply {

            rvLanguage.adapter = langAdapter
            rvLanguage.layoutManager = LinearLayoutManager(requireContext())
            rvLanguage.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

            this@LanguageBottomSheet.lifecycleScope.launchWhenStarted {
                vmLanguageSheet.langList.collectLatest { langs ->
                    langAdapter.submitList(langs)
                }
            }

            this@LanguageBottomSheet.lifecycleScope.launchWhenStarted {
                vmLanguageSheet.mode.collectLatest {
                    val mode = it ?: return@collectLatest
                    (root as ViewGroup).redrawViewGroup(mode)
                }
            }

            this@LanguageBottomSheet.lifecycleScope.launchWhenStarted {
                vmLanguageSheet.nativeLang.collectLatest {
                    langAdapter.updateNativeLang(it)
                }
            }

            vmLanguageSheet.nativeOrForeign.observe(viewLifecycleOwner) {
                tvTitle.text = if (it == NATIVE_LANGUAGE_CHANGE) {
                    getString(LocalizationHelper.nativeLanguage.getIdByLang(vmLanguageSheet.nativeLang.value))
                } else {
                    getString(LocalizationHelper.learnedLanguege.getIdByLang(vmLanguageSheet.nativeLang.value))
                }
            }
        }
    }
}







