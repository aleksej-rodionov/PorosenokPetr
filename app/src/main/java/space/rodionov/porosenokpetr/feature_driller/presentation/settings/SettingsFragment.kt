package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.MainActivity
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.FragmentSettingsBinding
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter.SettingsAdapter
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
//            checkSwitchWithTime = { millis, isChecked ->
//                // todo а надо ли это?
//            },
            openTimePicker = { openTimePicker() }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        binding.apply {
            recyclerView.adapter = settingsAdapter
            settingsAdapter.submitList(SettingsHelper.getSettingsMenu())
            recyclerView.setHasFixedSize(true)
//            recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
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
            // todo collect ui events
        }
    }

    private fun checkSwitch(type: SettingsSwitchType, isChecked: Boolean) {
        Log.d(TAG_SETTINGS, "checkSwitch: ${type.name}, $isChecked")
    }

    private fun openTimePicker() {
        Log.d(TAG_SETTINGS, "openTimePicker: CALLED")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





