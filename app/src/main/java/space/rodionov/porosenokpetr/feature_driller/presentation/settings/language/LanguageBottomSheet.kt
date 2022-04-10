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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.BottomsheetLanguageBinding
import space.rodionov.porosenokpetr.feature_driller.utils.AppFlavor
import space.rodionov.porosenokpetr.feature_driller.utils.Constants

@AndroidEntryPoint
class LanguageBottomSheet: BottomSheetDialogFragment(), RadioGroup.OnCheckedChangeListener {

    companion object {
        const val LANGUAGE_BOTTOM_SHEET = "languageBottomSheet"
    }

    private val binding: BottomsheetLanguageBinding by lazy {
        BottomsheetLanguageBinding.inflate(layoutInflater)
    }

    private val vmLanguageSheet: LanguageBottomsheetViewModel by viewModels()

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
            vmLanguageSheet.nativeOrForeign.value = it.getInt("nativeForeign") // todo pass it from the Fragment
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
            this@LanguageBottomSheet.lifecycleScope.launchWhenStarted {
                vmLanguageSheet.langList.collectLatest { langs ->
                    radioGroup.setOnCheckedChangeListener(null)
                    radioGroup.removeAllViews()

                    val natLang = vmLanguageSheet.nativeLang.value
                    langs.forEach { lang ->
                        val rb = RadioButton(requireContext())
                        rb.text = lang.getLocalizedName(natLang)
                        rb.isChecked = lang.langIndex == natLang
                        radioGroup.addView(rb)
                    }
//                    radioGroup.redrawRadios(fetchColors(vmLanguageSheet.mode.value, resources)) // todo убрать отсюда

                    radioGroup.setOnCheckedChangeListener(this@LanguageBottomSheet)
                }
            }
        }
    }

    override fun onCheckedChanged(rb: RadioGroup?, index: Int) {
        vmLanguageSheet.onLangChecked(index)
        dismiss()
    }
}







