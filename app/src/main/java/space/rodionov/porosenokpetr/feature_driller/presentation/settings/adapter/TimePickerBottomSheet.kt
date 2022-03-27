package space.rodionov.porosenokpetr.feature_driller.presentation.settings.adapter

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.redrawViewGroup
import space.rodionov.porosenokpetr.databinding.BottomsheetTimePickerBinding
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.SettingsViewModel
import space.rodionov.porosenokpetr.feature_driller.utils.Constants

class TimePickerBottomSheet: BottomSheetDialogFragment() {

    companion object {
        const val TIME_PICKER_BOTTOM_SHEET = "timePickerBottomSheet"
    }

    private val binding: BottomsheetTimePickerBinding by lazy {
        BottomsheetTimePickerBinding.inflate(layoutInflater)
    }

    private val vmSettings: SettingsViewModel by viewModels({ requireParentFragment() })

    override fun getTheme(): Int = vmSettings.mode.value?.let {
        when (it) {
            Constants.MODE_LIGHT -> R.style.Theme_NavBarDay
            Constants.MODE_DARK -> R.style.Theme_NavBarNight
            else -> R.style.Theme_NavBarDay
        }
    } ?: R.style.Theme_NavBarDay

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view.parent as ViewGroup).setBackgroundColor(Color.TRANSPARENT)
        binding.apply {
            this@TimePickerBottomSheet.lifecycleScope.launchWhenStarted {
//              todo  vmSettings.notify
            }
            this@TimePickerBottomSheet.lifecycleScope.launchWhenStarted {
//          todo      vmSettings.notificationTime
            }

            this@TimePickerBottomSheet.lifecycleScope.launchWhenStarted {
                //todo vmSettings.eventFlow -> collect events
            }

            this@TimePickerBottomSheet.lifecycleScope.launchWhenStarted {
                vmSettings.mode.collectLatest {
                    val mode = it ?: return@collectLatest
                    (root as ViewGroup).redrawViewGroup(mode)
                }
            }
        }
    }

    // todo fun onTimeChanged()

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        // todo vmSettings.acceptTimeChange()
    }
}






