package space.rodionov.porosenokpetr.main.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.core.presentation.LocalNativeLanguage
import space.rodionov.porosenokpetr.core.util.Constants.MODE_DARK
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.main.PorosenokPetrApp
import space.rodionov.porosenokpetr.main.di.DaggerRootComponent
import space.rodionov.porosenokpetr.main.navigation.CustomBottomNavigation
import space.rodionov.porosenokpetr.main.navigation.MainNavHost
import space.rodionov.porosenokpetr.ui.theme.Gray200
import space.rodionov.porosenokpetr.ui.theme.Gray900
import space.rodionov.porosenokpetr.ui.theme.PorosenokPetrTheme
import javax.inject.Inject

class RootActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val vmMain by this.viewModels<RootViewModel>(
        factoryProducer = { factory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val component = DaggerRootComponent
            .builder()
            .appComponent(PorosenokPetrApp.component!!)
            .build()
        component.inject(this)
        super.onCreate(savedInstanceState)

        setContent {

            val state = vmMain.state

            PorosenokPetrTheme(darkTheme = state.isDarkTheme == MODE_DARK) {

                val nativeLanguage = LocalNativeLanguage.current
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = if (state.isDarkTheme == MODE_DARK) Gray900 else Gray200,
                        darkIcons = state.isDarkTheme != MODE_DARK
                    )
                    systemUiController.setNavigationBarColor(
                        color = if (state.isDarkTheme == MODE_DARK) Gray900 else Gray200,
                        darkIcons = state.isDarkTheme != MODE_DARK
                    )
                }

                nativeLanguage.current = state.nativeLanguage

                key(state.isDarkTheme) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        bottomBar = { CustomBottomNavigation(navController = navController) }
                    ) {

                        MainNavHost(
                            modifier = Modifier.padding(it),
                            navController = navController,
                            scaffoldState = scaffoldState,
                            viewModelOwner = this,
                            viewModelFactory = factory
                        )
                    }
                }
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
}





