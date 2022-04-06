package space.rodionov.porosenokpetr.feature_driller.utils

import android.content.res.Resources
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.feature_driller.domain.utils.StringTag

data class LocalizedString(
    val stringTag: StringTag,
    val ruId: Int,
    val uaId: Int
)

object LocalizationHelper {

    fun getLocalizedStrings() = mutableListOf<LocalizedString>().apply {
        add(LocalizedString(StringTag.TRANSLATION_DIRECTION, R.string.translation_direction, R.string.translation_direction_ua))
        add(LocalizedString(StringTag.DARK_MODE, R.string.dark_mode, R.string.dark_mode_ua))
        add(LocalizedString(StringTag.FOLLOW_SYSTEM_MODE, R.string.follow_system_mode, R.string.follow_system_mode_ua))
        add(LocalizedString(StringTag.REMINDER, R.string.reminder, R.string.reminder_ua))
        add(LocalizedString(StringTag.REMIND, R.string.remind, R.string.remind_ua))
        add(LocalizedString(StringTag.NATIVE_LANGUAGE, R.string.russian, R.string.ukrainian_ua)) // note here's a diff

        add(LocalizedString())
    }
}
