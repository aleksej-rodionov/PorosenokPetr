package space.rodionov.porosenokpetr

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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
import space.rodionov.porosenokpetr.databinding.SnackbarLayoutBinding
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.DEFAULT_INT
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

    private fun scheduleSuccessSnackBar(notificationTime: Long) {
        val titleNotificationSchedule = getString(R.string.notification_schedule_title)
        val patternNotificationSchedule = getString(R.string.notification_schedule_pattern)
//        Snackbar.make(
//            binding.root,
//            titleNotificationSchedule + SimpleDateFormat(
//                patternNotificationSchedule, Locale.getDefault()
//            ).format(notificationTime).toString(),
//            Snackbar.LENGTH_LONG
//        ).show()
        showSnackBar(DEFAULT_INT, titleNotificationSchedule + SimpleDateFormat(
                patternNotificationSchedule, Locale.getDefault()
            ).format(notificationTime).toString())
    }

    private fun scheduleErrornackbar() {
//        val errorNotificationSchedule = getString(R.string.notification_schedule_error)
//        Snackbar.make(binding.root, errorNotificationSchedule, Snackbar.LENGTH_LONG).show()
        showSnackBar(R.string.notification_schedule_error, "")
    }

    private fun showSnackBar(resId: Int, text: String) {
        val snackBar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        val snackBarLayout =
            SnackbarLayoutBinding.bind(layoutInflater.inflate(R.layout.snackbar_layout, null))
        if (resId != DEFAULT_INT) {
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
}





