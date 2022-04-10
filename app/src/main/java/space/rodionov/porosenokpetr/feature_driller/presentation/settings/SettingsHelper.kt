package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import space.rodionov.porosenokpetr.feature_driller.domain.models.*
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.language.LanguageHelper
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.LocalizationHelper
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsItemType

object SettingsHelper {

    fun getSettingsMenu() = mutableListOf<BaseModel>().apply {
        add(Header(null, LocalizationHelper.translationDirection))
        add(MenuSwitch(
            null,
            SettingsItemType.TRANSLATION_DIRECTION,
            LocalizationHelper.fromForeignToNative,
            false
        ))
        add(MenuLanguage(
            SettingsItemType.CHANGE_NATIVE_LANG,
            LocalizationHelper.nativeLanguageSettings,
            LanguageHelper.russian
        ))
        add(MenuLanguage(
            SettingsItemType.CHANGE_LEARNED_LANG,
            LocalizationHelper.learnedLanguageSettings,
            LanguageHelper.english
        ))
        add(Header(null, LocalizationHelper.appearance))
        add(MenuSwitch(
            null,
            SettingsItemType.NIGHT_MODE,
            LocalizationHelper.darkMode,
            false
        ))
        add(MenuSwitch(
            null,
            SettingsItemType.SYSTEM_MODE,
            LocalizationHelper.followSystemMode,
            false
        ))
        add(Header(null, LocalizationHelper.reminder))
        add(MenuSwitchWithTimePicker(
            null,
            SettingsItemType.REMINDER,
            LocalizationHelper.remind,
            false,
            Constants.MILLIS_IN_NINE_HOURS
        ))
//        add(Header(null, LocalizationHelper.nativeLanguage))
//        add(MenuSwitch(
//            null,
//            SettingsSwitchType.NATIVE_LANG,
//            LocalizationHelper.currentNativeLanguage,
//            false
//        ))
    }
}