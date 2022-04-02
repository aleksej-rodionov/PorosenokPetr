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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_DARK
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.databinding.ActivityMainBinding
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_RU
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_UA
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_NATIVE_LANG
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val vmMain: MainViewModel by viewModels()

//    private var systemNavBarHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(binding.navHostFragment) { v, insets ->
            v.updatePadding(
                top = insets.systemWindowInsetTop,
                bottom = insets.systemWindowInsetBottom
            )
//            systemNavBarHeight = insets.systemWindowInsetBottom
//            Log.d(TAG_PETR, "onCreate: snbh = $systemNavBarHeight")
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
            vmMain.nativeLanguage.collectLatest {
                if (!vmMain.followSystemLocale.value) {
                    updateLocale(when (it) {
                        NATIVE_LANGUAGE_UA -> "uk"
                        else -> "ru"
                    })
                }
            }
        }

        this.lifecycleScope.launchWhenStarted {
            vmMain.followSystemLocale.collectLatest {
                if (it) {
                    vmMain.updateNativeLanguage(getSystemLang()) // хз зачем. Обновляет lang в datastore
                    updateLocale(getSystemLocale())
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG_NATIVE_LANG, "onConfigurationChanged: CALLED")
        if (vmMain.followSystemMode.value) {
            Log.d(TAG_NATIVE_LANG, "onConfigurationChanged: followSysMode = true")
            vmMain.updateMode(getSystemTheme())
        }
        if (vmMain.followSystemLocale.value) {

            vmMain.updateNativeLanguage(getSystemLang()) // хз работает ли. Обновляет according to system locale
            updateLocale(getSystemLocale()) // todo не пойму оно работает нет?
            Log.d(TAG_NATIVE_LANG, "onConfigurationChanged: update locale!")
        }
    }

    private fun getSystemLang() : Int {
        return when (resources.configuration.locale.language) {
            "uk" -> {
                Log.d(TAG_NATIVE_LANG, "getSystemLang: ${resources.configuration.locale.language}")
                NATIVE_LANGUAGE_UA
            }
            else -> {
                Log.d(TAG_NATIVE_LANG, "getSystemLang: ${resources.configuration.locale.language}")
                NATIVE_LANGUAGE_RU
            }
        }
    }

    private fun getSystemLocale() : String {
        return when (resources.configuration.locale.language) {
            "uk" -> {
                Log.d(TAG_NATIVE_LANG, "getSystemLocale: ${resources.configuration.locale.language}")
                "uk"
            }
            else -> {
                Log.d(TAG_NATIVE_LANG, "getSystemLocale: ${resources.configuration.locale.language}")
                "ru"
            }
        }
    }

    private fun updateLocale(locale: String) {
        Log.d(TAG_NATIVE_LANG, "updateLocale: $locale")
        val config = resources.configuration
        val newLocale = Locale(locale)
        Locale.setDefault(newLocale)
        config.locale = newLocale
        resources.updateConfiguration(config, resources.displayMetrics)
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
}





