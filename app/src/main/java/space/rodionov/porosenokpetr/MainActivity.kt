package space.rodionov.porosenokpetr

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.updatePadding
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.porosenokpetr.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

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

        setDefaultBarsColors(false) // todo это надо будет воткнуть в Обсервер темы
    }

    private fun setDefaultBarsColors(isNight: Boolean) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (isNight) {
                true -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.gray900)
                    window.navigationBarColor = ContextCompat.getColor(this, R.color.gray900)
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                }
                else -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.gray100)
                    window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
            }
//        }
    }
}





