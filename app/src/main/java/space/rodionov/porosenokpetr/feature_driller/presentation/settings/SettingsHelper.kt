package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.feature_driller.domain.models.*
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.LocalizationHelper
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType

object SettingsHelper {

    fun getSettingsMenu() = mutableListOf<BaseModel>().apply {
        add(Header(null, LocalizationHelper.translationDirection))
        add(MenuSwitch(
            null,
            SettingsSwitchType.TRANSLATION_DIRECTION,
            LocalizationHelper.fromForeignToNative,
            false
        ))
        add(Header(null, LocalizationHelper.appearance))
        add(MenuSwitch(
            null,
            SettingsSwitchType.NIGHT_MODE,
            LocalizationHelper.darkMode,
            false
        ))
        add(MenuSwitch(
            null,
            SettingsSwitchType.SYSTEM_MODE,
            LocalizationHelper.followSystemMode,
            false
        ))
        add(Header(null, LocalizationHelper.reminder))
        add(MenuSwitchWithTimePicker(
            null,
            SettingsSwitchType.REMINDER,
            LocalizationHelper.remind,
            false,
            Constants.MILLIS_IN_NINE_HOURS
        ))
        add(Header(null, LocalizationHelper.nativeLanguage))
        add(MenuSwitch(
            null,
            SettingsSwitchType.NATIVE_LANG,
            LocalizationHelper.currentNativeLanguage,
            false
        ))
    }
}