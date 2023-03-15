package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.language.LanguageHelper
import space.rodionov.porosenokpetr.core.util.Constants
import space.rodionov.porosenokpetr.core.util.LocalizationHelper
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsItemType
import space.rodionov.porosenokpetr.feature_settings.domain.model.*

object SettingsHelper {

    fun getSettingsMenu() = mutableListOf<BaseModel>().apply {
        add(Header(null, LocalizationHelper.language))
        add(
            MenuSwitch(
                null,
                SettingsItemType.TRANSLATION_DIRECTION,
                LocalizationHelper.fromForeignToNative,
                false
            )
        )

        if (BuildConfig.FLAVOR == "swedishdriller"){add(
            MenuLanguage(
                SettingsItemType.CHANGE_NATIVE_LANG,
                LocalizationHelper.nativeLanguageSettings,
                LanguageHelper.russian
            )
        )
        add(
            MenuLanguage(
                SettingsItemType.CHANGE_LEARNED_LANG,
                LocalizationHelper.learnedLanguageSettings,
                LanguageHelper.english
            )
        )
    }

        add(Header(null, LocalizationHelper.appearance))
        add(
            MenuSwitch(
            null,
            SettingsItemType.NIGHT_MODE,
            LocalizationHelper.darkMode,
            false
        )
        )
        add(
            MenuSwitch(
            null,
            SettingsItemType.SYSTEM_MODE,
            LocalizationHelper.followSystemMode,
            false
        )
        )
        add(Header(null, LocalizationHelper.reminder))
        add(
            MenuSwitchWithTimePicker(
            null,
            SettingsItemType.REMINDER,
            LocalizationHelper.remind,
            false,
            Constants.MILLIS_IN_NINE_HOURS
        )
        )
//        add(Header(null, LocalizationHelper.nativeLanguage))
//        add(MenuSwitch(
//            null,
//            SettingsSwitchType.NATIVE_LANG,
//            LocalizationHelper.currentNativeLanguage,
//            false
//        ))
    }
}