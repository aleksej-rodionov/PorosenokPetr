package space.rodionov.porosenokpetr.core

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_NATIVE_LANG
import java.util.*


fun getLocaleStringResource(requestedLocale: Locale, resourceId: Int, context: Context): String? {
    val config = Configuration(context.resources.configuration)
    config.setLocale(requestedLocale)
    val resultString = context.createConfigurationContext(config).getText(resourceId).toString()
    return resultString
}