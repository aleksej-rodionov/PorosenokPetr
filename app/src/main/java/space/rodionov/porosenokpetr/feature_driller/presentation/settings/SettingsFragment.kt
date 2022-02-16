package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.Constants
import space.rodionov.porosenokpetr.Constants.MODE_DARK
import space.rodionov.porosenokpetr.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.Constants.TAG_PETR
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.FragmentSettingsBinding
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseFragment

@AndroidEntryPoint
class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding

    private val vmSettings: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        binding?.apply {

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                vmMain.transDir.collectLatest {
                    val nativeToForeign = it ?: return@collectLatest
                    val transDirText =
                        if (nativeToForeign) resources.getString(R.string.from_ru_to_en)
                        else resources.getString(R.string.from_en_to_ru)
                    switchTransdir.text = transDirText
                    switchTransdir.setOnCheckedChangeListener(null)
                    switchTransdir.isChecked = nativeToForeign
                    switchTransdir.setOnCheckedChangeListener { _, isChecked ->
                        vmMain.updateTransDir(isChecked)
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                vmMain.mode.collectLatest {
                    val mode = it ?: return@collectLatest
                    Log.d(Constants.TAG_PETR, "mode.collect (SettingsFragment) = $it")
                    switchMode.setOnCheckedChangeListener(null)
                    switchMode.isChecked = mode == MODE_DARK
                    switchMode.setOnCheckedChangeListener { _, isChecked ->
                        if (!vmMain.followSystemMode.value) vmMain.saveMode(if (isChecked) MODE_DARK else MODE_LIGHT)
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                vmMain.followSystemMode.collectLatest {
                    val follow = it ?: return@collectLatest
                    Log.d(TAG_PETR, "follow.collect in settingsFragment follow = $follow")

                    if (follow) {
                        switchMode.setTextColor(resources.getColor(R.color.gray600))
                        switchMode.thumbTintList = ColorStateList.valueOf(resources.getColor(R.color.gray600))
                        switchMode.isEnabled = false
                    } else {
                        switchMode.setTextColor(resources.getColor(R.color.gray600))
//                        switchMode.setTextColor(colors[2]))
                        switchMode.thumbTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
                        switchMode.isEnabled = true
                    }

                    switchFollowSystemMode.setOnCheckedChangeListener(null)
                    switchFollowSystemMode.isChecked = follow
                    switchFollowSystemMode.setOnCheckedChangeListener { _, isChecked ->
                        vmMain.saveFollowSystemMode(isChecked)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





