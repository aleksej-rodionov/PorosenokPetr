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
import space.rodionov.porosenokpetr.core.getLocalizedString
import space.rodionov.porosenokpetr.databinding.FragmentSettingsBinding
import space.rodionov.porosenokpetr.databinding.SnackbarLayoutBinding
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter.SettingsAdapter
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANG_POSTFIX_RU
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANG_POSTFIX_UA
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_DARK
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_UA
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_NATIVE_LANG
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType
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
            onTimePickerClick = { onTimePickerClick() }
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
                vmSettings.updateMenuList(SettingsSwitchType.TRANSLATION_DIRECTION, it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.mode.collectLatest {
                settingsAdapter.updateMode(it)
                vmSettings.updateMenuList(SettingsSwitchType.NIGHT_MODE, it == MODE_DARK)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.followSystemMode.collectLatest {
                vmSettings.updateMenuList(SettingsSwitchType.SYSTEM_MODE, it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.remind.collectLatest {
                vmSettings.updateMenuList(SettingsSwitchType.REMINDER, it)

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
                val localeLang = if (it==1) LANG_POSTFIX_UA else LANG_POSTFIX_RU
                Log.d(TAG_NATIVE_LANG, "initViewModel: lang = $localeLang")
                binding.tvTitle.text = getLocalizedString(Locale(localeLang), R.string.settings, requireContext())


                settingsAdapter.updateNativeLang(it)
                vmSettings.updateMenuList(SettingsSwitchType.NATIVE_LANG, it == NATIVE_LANGUAGE_UA)
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

    private fun checkSwitch(type: SettingsSwitchType, isChecked: Boolean) {
        vmSettings.checkSwitch(type, isChecked)
    }

    private fun onTimePickerClick() {
        vmSettings.openTimePicker()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





