package space.rodionov.porosenokpetr.util

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import space.rodionov.porosenokpetr.R

fun fetchTheme(isNight: Boolean, res: Resources): Resources.Theme {
    return when (isNight) {
        true -> {
            val theme = res.newTheme()
//            theme.applyStyle(R.style.MyNightTheme, false)
            theme
        }
        else -> {
            val theme = res.newTheme()
//            theme.applyStyle(R.style.MyDayTheme, false)
            theme
        }
    }
}

fun ViewGroup.redrawViewGroup(isNight: Boolean) {

}