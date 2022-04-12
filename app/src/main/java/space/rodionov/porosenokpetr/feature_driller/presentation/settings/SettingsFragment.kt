package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.MainActivity
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.FragmentSettingsBinding
import space.rodionov.porosenokpetr.databinding.SnackbarLayoutBinding
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter.SettingsAdapter
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.language.LanguageBottomSheet
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.language.LanguageHelper
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_DARK
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_UA
import space.rodionov.porosenokpetr.feature_driller.utils.LocalizationHelper
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsItemType
import java.util.*

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val vmSettings: SettingsViewModel by viewModels()
//    private val applicationContext = (activity as MainActivity).applicationContext

    private val settingsAdapter: SettingsAdapter by lazy {
        SettingsAdapter(
            checkSwitch = { type, isChecked ->
                checkSwitch(type, isChecked)
            },
            onTimePickerClick = { onTimePickerClick() },
            onItemClick = { type ->
                onSetingsItemClick(type)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        binding.apply {
            recyclerView.adapter = settingsAdapter
            recyclerView.setHasFixedSize(true)

            btnBack.setOnClickListener {
                (activity as MainActivity).onBackPressed()
            }
            initViewModel()
        }
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.menuListFlow.collectLatest {
                val list = it?: return@collectLatest
                settingsAdapter.submitList(list)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.transDir.collectLatest {
                vmSettings.updateMenuList(SettingsItemType.TRANSLATION_DIRECTION, it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.mode.collectLatest {
                settingsAdapter.updateMode(it)
                vmSettings.updateMenuList(SettingsItemType.NIGHT_MODE, it == MODE_DARK)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.followSystemMode.collectLatest {
                vmSettings.updateMenuList(SettingsItemType.SYSTEM_MODE, it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.remind.collectLatest {
                vmSettings.updateMenuList(SettingsItemType.REMINDER, it)

                Log.d(Constants.TAG_PETR, "remind.collect: justopened = ${vmSettings.justOpened}")
                if (it) {
                    vmSettings.buildAndScheduleNotification().apply {
                        if (!vmSettings.justOpened) {
                            this?.let { timestamp ->
                                vmSettings.scheduleSuccessSnackBar(timestamp, getString(R.string.notification_schedule_title), getString(R.string.notification_schedule_pattern))
                            } ?: vmSettings.scheduleErrorSnackbar(resources.getString(R.string.notification_schedule_error))
                        }
                    }
                } else {
                    vmSettings.cancelNotification()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.notificationTime.collectLatest {
                vmSettings.updateNotificationTimeInList(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.nativeLanguage.collectLatest {
                binding.tvTitle.text = resources.getString( if (it == LANGUAGE_UA) LocalizationHelper.settings.uaId
                else LocalizationHelper.settings.ruId)


                settingsAdapter.updateNativeLang(it)
                vmSettings.updateMenuList(SettingsItemType.NATIVE_LANG, it == LANGUAGE_UA)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.learnedLanguage.collectLatest {
//                settingsAdapter.updateLearnedLang(it)
                val lang = LanguageHelper.getLangByIndex(it)
                vmSettings.updateMenuItemsInList(SettingsItemType.CHANGE_LEARNED_LANG, lang)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.settingsEventFlow.collectLatest { event ->
                when (event) {
                    is SettingsViewModel.SettingsEvent.OpenTimePicker -> {
                        TimePickerBottomSheet().show(
                            childFragmentManager,
                            TimePickerBottomSheet.TIME_PICKER_BOTTOM_SHEET
                        )
                    }
                    is SettingsViewModel.SettingsEvent.ShowSnackbar -> {
                        showSnackBar(Constants.DEFAULT_INT, event.text)
                    }
                    is SettingsViewModel.SettingsEvent.OnChangeLang -> {
                        val args = Bundle()
                        val langToChange = event.nativeOrForeign
                        args.putInt("nativeOrForeign", langToChange)

                        val languageBottomSheet = LanguageBottomSheet()
                        languageBottomSheet.arguments = args
                        languageBottomSheet.show(
                            requireFragmentManager(), // здесь не просто фрагмент манагер?
                            LanguageBottomSheet.LANGUAGE_BOTTOM_SHEET
                        )
                    }
                }
            }
        }
    }

    private fun showSnackBar(resId: Int, text: String) {
        val snackBar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        val snackBarLayout =
            SnackbarLayoutBinding.bind(layoutInflater.inflate(R.layout.snackbar_layout, null))
        if (resId != Constants.DEFAULT_INT) {
            snackBarLayout.tvText.setText(resId)
        } else {
            snackBarLayout.tvText.text = text
        }
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                R.color.transparent,
                null
            )
        )
        val params = snackBarView.layoutParams
        snackBarView.layoutParams = params
        (snackBarView as Snackbar.SnackbarLayout).addView(snackBarLayout.root, 0)
        snackBar.anchorView = binding.frameBottom
        snackBar.show()
    }

    private fun checkSwitch(type: SettingsItemType, isChecked: Boolean) {
        vmSettings.checkSwitch(type, isChecked)
    }

    private fun onTimePickerClick() {
        vmSettings.openTimePicker()
    }

    private fun onSetingsItemClick(type: SettingsItemType) {
        vmSettings.onChangeLang(type)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





