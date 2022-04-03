package space.rodionov.porosenokpetr.core

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_NATIVE_LANG
import java.util.*


fun getLocalizedString(requestedLocale: Locale, resourceId: Int, context: Context): String? {
    val newConfig = Configuration(context.resources.configuration)
    newConfig.setLocale(requestedLocale)
    Log.d(TAG_NATIVE_LANG, "getLocaleStringResource: check new locale = ${newConfig.locale}")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        newConfig.locales[1]?.let {
            Log.d(TAG_NATIVE_LANG, "getLocaleStringResource: check new locales[1] = ${it}")
        }
        Log.d(TAG_NATIVE_LANG, "getLocaleStringResource: check new locales[0] = ${newConfig.locales[0]}")
    }
    val newContext = context.createConfigurationContext(newConfig)
    Log.d(TAG_NATIVE_LANG, "getLocaleStringResource: check new context: ${
        newContext.resources.configuration.locale
    }")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        newContext.resources.configuration.locales[1]?.let {
            Log.d(TAG_NATIVE_LANG, "getLocaleStringResource: check new locales[1] = ${it}")
        }
        Log.d(TAG_NATIVE_LANG, "getLocaleStringResource: check new locales[0] = ${
            newContext.resources.configuration.locales[0]
        }")
    }
    val newString = newContext.getString(resourceId)
    Log.d(TAG_NATIVE_LANG, "getLocaleStringResource: check new string = $newString")
    return newString
}