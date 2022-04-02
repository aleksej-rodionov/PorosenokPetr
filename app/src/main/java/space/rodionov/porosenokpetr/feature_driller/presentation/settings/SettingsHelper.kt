package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.feature_driller.domain.models.*
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

object SettingsHelper {

    fun getSettingsMenu() = mutableListOf<BaseModel>().apply {
        add(Header(null, R.string.translation_direction))
        add(MenuSwitch(
//            R.string.translation_direction, //todo remove parameter
            null,
            SettingsSwitchType.TRANSLATION_DIRECTION,
            R.string.from_en_to_ru,
            false
        ))
        add(Header(null, R.string.appearance))
        add(MenuSwitch(
//            R.string.translation_direction, //todo remove parameter
            null,
            SettingsSwitchType.NIGHT_MODE,
            R.string.dark_mode,
            false
        ))
        add(MenuSwitch(
//            R.string.translation_direction, //todo remove parameter
            null,
            SettingsSwitchType.SYSTEM_MODE,
            R.string.follow_system_mode,
            false
        ))
        add(Header(null, R.string.reminder))
        add(MenuSwitchWithTimePicker(
//            R.string.reminder, //todo remove parameter
            null,
            SettingsSwitchType.REMINDER,
            R.string.remind,
            false,
            Constants.MILLIS_IN_NINE_HOURS
        ))
        add(Header(null, R.string.native_language))
        add(MenuSwitch(
            null,
            SettingsSwitchType.NATIVE_LANG,
            R.string.russian,
            false
        ))
        add(MenuSwitch(
            null,
            SettingsSwitchType.SYSTEM_LANG,
            R.string.follow_system_locale,
            false
        ))
    }
}