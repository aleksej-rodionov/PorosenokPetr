package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.feature_driller.domain.models.*
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType
import space.rodionov.porosenokpetr.feature_driller.utils.StringTags
import space.rodionov.porosenokpetr.feature_driller.utils.StringTags.DARK_MODE
import space.rodionov.porosenokpetr.feature_driller.utils.StringTags.FOLLOW_SYSTEM_MODE
import space.rodionov.porosenokpetr.feature_driller.utils.StringTags.NATIVE_LANGUAGE
import space.rodionov.porosenokpetr.feature_driller.utils.StringTags.REMIND
import space.rodionov.porosenokpetr.feature_driller.utils.StringTags.REMINDER
import space.rodionov.porosenokpetr.feature_driller.utils.StringTags.TRANSLATION_DIRECTION

object SettingsHelper {

    fun getSettingsMenu() = mutableListOf<BaseModel>().apply {
        add(Header(null, R.string.translation_direction))
        add(MenuSwitch(
//            R.string.translation_direction,
            TRANSLATION_DIRECTION,
            null,
            SettingsSwitchType.TRANSLATION_DIRECTION,
            R.string.from_en_to_ru,
            false
        ))
        add(Header(null, R.string.appearance))
        add(MenuSwitch(
//            R.string.dark_mode,
            DARK_MODE,
            null,
            SettingsSwitchType.NIGHT_MODE,
            R.string.dark_mode,
            false
        ))
        add(MenuSwitch(
//            R.string.follow_system_mode,
            FOLLOW_SYSTEM_MODE,
            null,
            SettingsSwitchType.SYSTEM_MODE,
            R.string.follow_system_mode,
            false
        ))
        add(Header(null, R.string.reminder))
        add(MenuSwitchWithTimePicker(
//            R.string.reminder,
            REMINDER,
            null,
            SettingsSwitchType.REMINDER,
//            R.string.remind,
            REMIND,
            false,
            Constants.MILLIS_IN_NINE_HOURS
        ))
        add(Header(null, R.string.native_language))
        add(MenuSwitch(
//            R.string.native_language,
            NATIVE_LANGUAGE,
            null,
            SettingsSwitchType.NATIVE_LANG,
            R.string.russian,
            false
        ))
    }
}