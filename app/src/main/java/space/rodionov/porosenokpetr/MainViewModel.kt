package space.rodionov.porosenokpetr

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_PETR
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeModeUseCase: ObserveModeUseCase,
    private val setModeUseCase: SetModeUseCase,
    private val observeFollowSystemModeUseCase: ObserveFollowSystemModeUseCase,
    private val observeReminderUseCase: ObserveReminderUseCase
): ViewModel() {
    private val sdf = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

    //==========================MODE=========================================
    private val _mode = observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun updateMode(mode:Int) = viewModelScope.launch {
        setModeUseCase.invoke(mode)
    }

    //==========================FOLLOW SYSTEM MODE=========================================
    private val _followSystemMode = observeFollowSystemModeUseCase.invoke()
    val followSystemMode = _followSystemMode.stateIn(viewModelScope, SharingStarted.Lazily, false)

    //==========================REMINDER=========================================
    private val _reminder = observeReminderUseCase.invoke()
    val reminder = _reminder.stateIn(viewModelScope, SharingStarted.Lazily, false)

    //=============================METHODS======================================

    fun findUpcomingNotificationTime(): Long {
        val curTimeStamp: Long = System.currentTimeMillis() // today current millis
        val todayString = sdf.format(curTimeStamp)
        val todayStartTimeStamp = Calendar.getInstance().apply { this.time = sdf.parse(todayString) }.timeInMillis // today start
        val todayPlusTwentyOneTimeStamp = todayStartTimeStamp + 3600000 * 21 // today 21:00
        return if (curTimeStamp < todayPlusTwentyOneTimeStamp) {
            logNotificationTime(todayPlusTwentyOneTimeStamp)
            todayPlusTwentyOneTimeStamp
        } else {
            tomorrowPlusTwentyOneTimeStamp()
        }
    }

    private fun tomorrowPlusTwentyOneTimeStamp(): Long {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 1)
        val tomorrowString = sdf.format(cal.timeInMillis)
        val tomorrowStartTimeStamp = Calendar.getInstance()
            .apply { this.time = sdf.parse(tomorrowString) }.timeInMillis // tomorrow start
        val tomorrowPlusTwentyOneTimeStamp = tomorrowStartTimeStamp + 3600000 * 21 + 2700000 // tomorrow 21:00
        logNotificationTime(tomorrowPlusTwentyOneTimeStamp)
        return tomorrowPlusTwentyOneTimeStamp
    }

    private fun logNotificationTime(timestamp: Long) {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timestamp
        val date = cal.time
        Log.d(TAG_PETR, "logNotificationTime: $date")
    }
}