package space.rodionov.porosenokpetr.util

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import space.rodionov.porosenokpetr.R

object ModeCOnstants {
    const val MODE_LIGHT = 0
    const val MODE_DARK = 1
}

fun fetchTheme(mode: Int, res: Resources): Resources.Theme {
    val theme = res.newTheme()
    return when (mode) {
        0 -> {
            theme.applyStyle(R.style.ModeDark, false)
            theme
        }
        1 -> {
            theme.applyStyle(R.style.ModeLight, false)
            theme
        }

        else -> {
            theme.applyStyle(R.style.ModeDark, false)
            theme
        }
    }
}

fun Resources.Theme.fetchColors(): Array<Int> {

    val bgMainValue = TypedValue()
    this.resolveAttribute(R.attr.bg_main, bgMainValue, true)
    val bgMain = bgMainValue.data

    val bgBetaValue = TypedValue()
    this.resolveAttribute(R.attr.bg_beta, bgBetaValue, true)
    val bgBeta = bgBetaValue.data

    val bgContrastValue = TypedValue()
    this.resolveAttribute(R.attr.bg_contrast, bgContrastValue, true)
    val bgContrast = bgContrastValue.data

    val textMainValue = TypedValue()
    this.resolveAttribute(R.attr.text_main, textMainValue, true)
    val textMain = textMainValue.data

    val textBetaValue = TypedValue()
    this.resolveAttribute(R.attr.text_beta, textBetaValue, true)
    val textBeta = textBetaValue.data

    val bgAccentAlphaValue = TypedValue()
    this.resolveAttribute(R.attr.bg_accent_alpha, bgAccentAlphaValue, true)
    val bgAccentAlpha = bgAccentAlphaValue.data

    val bgAccentBravoValue = TypedValue()
    this.resolveAttribute(R.attr.bg_accent_bravo, bgAccentBravoValue, true)
    val bgAccentBravo = bgAccentBravoValue.data

    val bgAccentCharlieValue = TypedValue()
    this.resolveAttribute(R.attr.bg_accent_charlie, bgAccentCharlieValue, true)
    val bgAccentCharlie = bgAccentCharlieValue.data

    val bgAccentDeltaValue = TypedValue()
    this.resolveAttribute(R.attr.bg_accent_delta, bgAccentDeltaValue, true)
    val bgAccentDelta = bgAccentDeltaValue.data

    val colors = intArrayOf(
        bgMain,
        bgBeta,
        textMain,
        textBeta,
        bgAccentAlpha,
        bgAccentBravo,
        bgAccentCharlie,
        bgAccentDelta
    )
    return colors.toTypedArray()
}

fun fetchColors(mode: Int, res: Resources): Array<Int> {
    return fetchTheme(mode, res).fetchColors()
}

//======================REDRAW===============================

fun ViewGroup.redrawViewGroup(isNight: Boolean) {

}
























