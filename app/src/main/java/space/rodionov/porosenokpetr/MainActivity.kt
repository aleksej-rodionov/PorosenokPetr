package space.rodionov.porosenokpetr

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_DARK
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.databinding.ActivityMainBinding
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.work.NotificationHelper
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val vmMain: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(binding.navHostFragment) { v, insets ->
            v.updatePadding(
                top = insets.systemWindowInsetTop,
                bottom = insets.systemWindowInsetBottom
            )
            insets
        }
        WindowCompat.setDecorFitsSystemWindows(window, true)



        this.lifecycleScope.launchWhenStarted {
            vmMain.mode.collectLatest {
                val mode = it ?: return@collectLatest

                setDefaultBarsColors(mode)
            }
        }

        this.lifecycleScope.launchWhenStarted {
            vmMain.followSystemMode.collectLatest {
                if (it) vmMain.updateMode(getSystemTheme())
            }
        }

        this.lifecycleScope.launchWhenStarted {
            vmMain.reminder.collectLatest {
                Log.d(TAG_PETR, "onCreate: reminder = $it")
                if (it) {
                    vmMain.buildAndScheduleNotification().apply {
                        this?.let { timestamp->
                            scheduleSuccessSnackBar(timestamp)
                        } ?: scheduleErrornackbar()
                    }
                } else {
                    vmMain.cancelNotification()
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (vmMain.followSystemMode.value) {
            vmMain.updateMode(getSystemTheme())
        }
    }

    //=================NIGHT MODE=====================
    private fun getSystemTheme(): Int {
        return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> MODE_DARK
            Configuration.UI_MODE_NIGHT_NO -> MODE_LIGHT
            else -> MODE_LIGHT
        }
    }
    //=================NIGHT MODE=====================

    private fun setDefaultBarsColors(mode: Int) {
        when (mode) {
            MODE_LIGHT -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
            MODE_DARK -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.gray850)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.gray850)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            }
            else -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        }
    }

    /*private fun buildNotification() {
        val notificationTime = findUpcomingNotificationTime() // notification timestamp
        val currentTime = currentTimeMillis() // current timestamp

        if (notificationTime > currentTime) {
            val data = Data.Builder().putInt(NOTIFICATION_ID, 0).build()
            val delay = notificationTime - currentTime
//            val delay = ONE_MIN_IN_MILLIS
            scheduleNotification(delay, data)

//            notificationScheduledSnackBar(notificationTime)
        } else {
//            incorrectTimeSnackbar()
        }
    }

    private fun scheduleNotification(delay: Long, data: Data) {
        val notificationWork = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        val instanceWorkManager = WorkManager.getInstance(this)
        Log.d(TAG_NOTIFY, "scheduleNotification: instanceWorkManager.hashcode = ${instanceWorkManager.hashCode()}")
        instanceWorkManager.beginUniqueWork(
            NOTIFICATION_WORK,
            ExistingWorkPolicy.REPLACE,
            notificationWork
        ).enqueue()
    }*/

    fun scheduleSuccessSnackBar(notificationTime: Long) {
        val titleNotificationSchedule = getString(R.string.notification_schedule_title)
        val patternNotificationSchedule = getString(R.string.notification_schedule_pattern)
        Snackbar.make(
            binding.root,
            titleNotificationSchedule + SimpleDateFormat(
                patternNotificationSchedule, Locale.getDefault()
            ).format(notificationTime).toString(),
            Snackbar.LENGTH_LONG
        ).show()
    }

    fun scheduleErrornackbar() {
        val errorNotificationSchedule = getString(R.string.notification_schedule_error)
        Snackbar.make(binding.root, errorNotificationSchedule, Snackbar.LENGTH_LONG).show()
    }
}





