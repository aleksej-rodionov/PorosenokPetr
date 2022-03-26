package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.MainActivity
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.FragmentSettingsBinding
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter.SettingsAdapter
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter.TimePickerBottomSheet
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_SETTINGS
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val vmSettings: SettingsViewModel by viewModels()

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
            settingsAdapter.submitList(SettingsHelper.getSettingsMenu())
            recyclerView.setHasFixedSize(true)
            Log.d(TAG_SETTINGS, "settings adapter: ${settingsAdapter.itemCount}")

            btnBack.setOnClickListener {
                (activity as MainActivity).onBackPressed()
            }
            initViewModel()
        }
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.transDir.collectLatest {
                settingsAdapter.setTransDir(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.mode.collectLatest {
                settingsAdapter.updateMode(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.followSystemMode.collectLatest {
                settingsAdapter.updateFollowSystemModeBA(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.remind.collectLatest {
                settingsAdapter.updateNotify(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmSettings.notificationTime.collectLatest {
                settingsAdapter.updateNotificationTime(it)
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
                }
            }
        }
    }

    private fun checkSwitch(type: SettingsSwitchType, isChecked: Boolean) {
        Log.d(TAG_SETTINGS, "checkSwitch: ${type.name}, $isChecked")
        vmSettings.checkSwitch(type, isChecked)
    }

    private fun onTimePickerClick() {
        Log.d(TAG_SETTINGS, "openTimePicker: CALLED")
        vmSettings.openTimePicker()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





