package space.rodionov.porosenokpetr.feature_launcher.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.core.util.startActivity
import space.rodionov.porosenokpetr.feature_launcher.di.DaggerLauncherComponent
import space.rodionov.porosenokpetr.main.PorosenokPetrApp
import space.rodionov.porosenokpetr.main.presentation.RootActivity
import space.rodionov.porosenokpetr.main.presentation.RootActivity.Companion.ROOT_ACTIVITY
import space.rodionov.porosenokpetr.ui.theme.Blue
import space.rodionov.porosenokpetr.ui.theme.PorosenokPetrTheme
import space.rodionov.porosenokpetr.ui.theme.White
import javax.inject.Inject

class LauncherActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel by viewModels<LauncherViewModel>(
        factoryProducer = { factory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val component = DaggerLauncherComponent
            .builder()
            .appComponent(PorosenokPetrApp.component!!)
            .build()
        component.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            PorosenokPetrTheme {

                val scaffoldState = rememberScaffoldState()

                LaunchedEffect(key1 = true) {
                    viewModel.uiEffect.collectLatest { effect ->
                        when (effect) {
                            is UiEffect.NavigateTo -> {
                                if (effect.route == ROOT_ACTIVITY) {
                                    startMainActivity()
                                } else {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        "Unknown destination"
                                    )
                                }
                            }

                            is UiEffect.ShowSnackbar -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    effect.msg.asString(this@LauncherActivity)
                                )
                            }

                            else -> Unit
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .background(color = Blue)
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontSize = 28.sp,
                            color = White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

    private fun startMainActivity() = startActivity<RootActivity>()
}