package space.rodionov.porosenokpetr.core

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.util.Log
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_NATIVE_LANG
import java.util.*


fun getLocalizedString(requestedLocale: Locale, resourceId: Int, context: Context): String? {
    val newConfig = Configuration(context.resources.configuration)
    newConfig.setLocale(requestedLocale)
    var newContext = context.createConfigurationContext(newConfig)
    Log.d(TAG_NATIVE_LANG, "getLocaleStringResource: check new context: ${newContext.resources.configuration.locale}")
    val newString = newContext.resources.getString(resourceId)
    Log.d(TAG_NATIVE_LANG, "getLocaleStringResource: check new string = $newString")
    newContext = null
    return newString
}