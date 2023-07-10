package space.rodionov.porosenokpetr.feature_splash.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.core.util.startActivity
import space.rodionov.porosenokpetr.feature_splash.di.DaggerSplashComponent
import space.rodionov.porosenokpetr.main.PorosenokPetrApp
import space.rodionov.porosenokpetr.main.presentation.RootActivity
import space.rodionov.porosenokpetr.ui.theme.Blue
import space.rodionov.porosenokpetr.ui.theme.PorosenokPetrTheme
import space.rodionov.porosenokpetr.ui.theme.White
import javax.inject.Inject

class SplashCustomActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel by viewModels<SplashCustomViewModel>(
        factoryProducer = { factory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val component = DaggerSplashComponent
            .builder()
            .appComponent(PorosenokPetrApp.component!!)
            .build()
        component.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            PorosenokPetrTheme {

                LaunchedEffect(key1 = true) {
                    viewModel.isDbPopulated.collectLatest {
                        if (it) startMainActivity()
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Blue
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
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