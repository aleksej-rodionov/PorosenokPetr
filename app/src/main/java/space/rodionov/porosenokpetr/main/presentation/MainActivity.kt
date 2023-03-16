package space.rodionov.porosenokpetr.main.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.main.PorosenokPetrApp
import space.rodionov.porosenokpetr.core.util.Constants.MODE_DARK
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.main.navigation.CustomBottomNavigation
import space.rodionov.porosenokpetr.main.navigation.MainNavGraph
import space.rodionov.porosenokpetr.ui.theme.PorosenokPetrTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val vmMain by viewModels<MainViewModel>(
        factoryProducer = { factory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        PorosenokPetrApp.component?.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            PorosenokPetrTheme {

                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { CustomBottomNavigation(navController = navController) }
                ) {

                    MainNavGraph(navController = navController)
                }
            }
        }


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
            }
            MODE_DARK -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.gray850)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.gray850)
            }
            else -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
            }
        }
    }
}





