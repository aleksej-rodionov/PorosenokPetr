package space.rodionov.porosenokpetr.core

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.TextView
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANG_POSTFIX_UA
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANG_POSTFIX_RU
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_NATIVE_LANG


fun TextView.setStringByArg(arg: String, lang: Int, res: Resources) {
    val langPostfix = if (lang == 1) LANG_POSTFIX_UA else LANG_POSTFIX_RU
    this.text = resources.getString(arg.getStringIdByLangPostfix(langPostfix, this.context))
}

fun TextView.setStringByTag(lang: Int, res: Resources) {
    val tag = this.tag?.toString()
    val langPostfix = if (lang == 1) LANG_POSTFIX_UA else LANG_POSTFIX_RU

    tag?.let {
        this.text = resources.getString(tag.getStringIdByLangPostfix(langPostfix, this.context))
    }
}

fun String.getStringIdByLangPostfix(langPostfix: String, context: Context): Int {
    val resId = context.resources.getIdentifier(
        "$this$langPostfix",
        "string",
        context.packageName
    )
    Log.d(TAG_NATIVE_LANG, "getStringId: $resId")
    return resId
}