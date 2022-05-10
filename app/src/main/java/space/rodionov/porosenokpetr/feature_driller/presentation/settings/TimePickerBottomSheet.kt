package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.PorosenokPetrApp
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.*
import space.rodionov.porosenokpetr.databinding.BottomsheetTimePickerBinding
import space.rodionov.porosenokpetr.feature_driller.di.ViewModelFactory
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import java.util.*
import javax.inject.Inject

class TimePickerBottomSheet: BottomSheetDialogFragment() {

    companion object {
        const val TIME_PICKER_BOTTOM_SHEET = "timePickerBottomSheet"
    }

    private val hourList = getHours()
    private val minuteList = getMinutes()

    private val binding: BottomsheetTimePickerBinding by lazy {
        BottomsheetTimePickerBinding.inflate(layoutInflater)
    }


//    private val vmSettings: SettingsViewModel by viewModels({ requireParentFragment() })
    @Inject
    lateinit var factory: ViewModelFactory //todo перенести в базовый фрагмент
    private val vmSettings by viewModels<SettingsViewModel>(
        ownerProducer = { requireParentFragment() },
        factoryProducer = { factory }
    )

    override fun onAttach(context: Context) {
        PorosenokPetrApp.component?.inject(this)
        super.onAttach(context)
    }

    override fun getTheme(): Int = vmSettings.mode.value.let {
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
            npMinutes.displayedValues = minuteList
            npMinutes.wrapSelectorWheel = false
            npMinutes.minValue = 0
            npMinutes.maxValue = minuteList.size - 1

            npHours.displayedValues = hourList
            npHours.wrapSelectorWheel = false
            npHours.minValue = 0
            npHours.maxValue = hourList.size - 1

            this@TimePickerBottomSheet.lifecycleScope.launchWhenStarted {
                vmSettings.notificationTime.collectLatest {
                    val hoursAndMinutes = parseLongToHoursAndMinutes(it)
                    val hours = hoursAndMinutes.first
                    val minutes = hoursAndMinutes.second.roundMinutesToFiveMinutes()
                    hourList.forEachIndexed { index, s ->
                        if (s == hours) npHours.value = index
                    }
                    minuteList.forEachIndexed { index, s ->
                        if (s == minutes) npMinutes.value = index
                    }
                }
            }

            this@TimePickerBottomSheet.lifecycleScope.launchWhenStarted {
                vmSettings.mode.collectLatest {
                    val mode = it ?: return@collectLatest
                    (root as ViewGroup).redrawViewGroup(mode)
                }
            }

            btnSelectCalendar.setOnClickListener {
                val h = hourList[npHours.value]
                val m = minuteList[npMinutes.value]
                val millis = hoursAndMinutesToMillis(h, m)
                vmSettings.updateNotificationTime(millis)
                vmSettings.cancelNotification()
                vmSettings.buildAndScheduleNotification(millis).apply {
                    this?.let { timestamp ->
                        vmSettings.scheduleSuccessSnackBar(timestamp, getString(R.string.notification_schedule_title), getString(R.string.notification_schedule_pattern))
                    } ?: vmSettings.scheduleErrorSnackbar(resources.getString(R.string.notification_schedule_error))
                }
                dismiss()
            }
        }
    }
}






