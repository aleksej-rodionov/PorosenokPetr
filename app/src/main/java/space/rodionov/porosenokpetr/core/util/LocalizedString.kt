package space.rodionov.porosenokpetr.core.util

import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_UA

data class LocalizedString(
    val stringTag: StringTag,
    val ruId: Int,
    val uaId: Int,
    val enId: Int? = null
) {
    fun getIdByLang(lang: Int) : Int {
        return when(lang) {
            LANGUAGE_RU -> ruId
            LANGUAGE_UA -> uaId
            LANGUAGE_EN -> enId ?: ruId
            else -> ruId
        }
    }
}

interface LangForAdapter {
    fun updateNativeLang(newLang: Int)
    fun updateLearnedLang(newLang: Int)
    fun getTagForLang() : String
}

object LocalizationHelper { // todo add english!

//    val top800words = LocalizedString(StringTag.TOP_800_WORDS, R.string.top_800_words, R.string.top_800_words_ua)
//    val top200verbs = LocalizedString(StringTag.TOP_200_VERBS, R.string.top_200_verbs, R.string.top_200_verbs_ua)
//    val usualWords = LocalizedString(StringTag.USUAL_WORDS, R.string.usual_words, R.string.usual_words_ua)
//    val usualVerbs = LocalizedString(StringTag.USUAL_VERBS, R.string.usual_verbs, R.string.usual_verbs_ua)
//    val phrasalVerbs = LocalizedString(StringTag.PHRASAL_VERBS, R.string.phrasal_verbs, R.string.phrasal_verbs_ua)
//    val phrases = LocalizedString(StringTag.PHRASES, R.string.phrases, R.string.phrases_ua)

    val itemCount = LocalizedString(StringTag.ITEM_COUNT, R.string.item_count, R.string.item_count_ua, R.string.item_count_en)
    val currentPosition = LocalizedString(StringTag.CURRENT_POSITION, R.string.current_position, R.string.current_position_ua, R.string.current_position_en)
    val onCardAppeared = LocalizedString(StringTag.ON_CARD_APPEARED, R.string.on_card_appeared, R.string.on_card_appeared_ua, R.string.on_card_appeared_en)
    val onCardDisappeared = LocalizedString(StringTag.ON_CARD_DISAPPEARED, R.string.on_card_disappeared, R.string.on_card_disappeared_ua, R.string.on_card_disappeared_en)

    val activeCategories = LocalizedString(StringTag.ACTIVE_CATEGORIES, R.string.active_categories, R.string.active_categories_ua, R.string.active_categories_en)
    val activateAllCategories = LocalizedString(StringTag.ACTIVATE_ALL_CATEGORIES, R.string.active_all_categories, R.string.active_categories_ua, R.string.active_all_categories_en)
    val cannotTurnAllCatsOff = LocalizedString(StringTag.CANNOT_TURN_ALL_CATS_OFF, R.string.cannot_turn_all_cats_off, R.string.cannot_turn_all_cats_off_ua, R.string.cannot_turn_all_cats_off_en)

    val collection = LocalizedString(StringTag.COLLECTION, R.string.collection, R.string.collection_ua, R.string.collection_en)
    val searchWord = LocalizedString(StringTag.SEARCH_WORD, R.string.search_word, R.string.search_word_ua, R.string.search_word_en)
    val searchAmongAllWords = LocalizedString(StringTag.SEARCH_AMONG_ALL_WORDS, R.string.search_among_all_words, R.string.search_among_all_words_ua, R.string.search_among_all_words_en)
    val searchIn = LocalizedString(StringTag.SEARCH_IN, R.string.search_in, R.string.search_in_ua, R.string.search_in_en)
    val percentage = LocalizedString(StringTag.PERCENTAGE, R.string.percentage, R.string.percentage_ua, R.string.percentage_en)

    val category = LocalizedString(StringTag.CATEGORY, R.string.category, R.string.category_ua, R.string.category_en)
    val wordInDialog = LocalizedString(StringTag.WORD_IN_DIALOG, R.string.word_in_dialog, R.string.word_in_dialog_ua, R.string.word_in_dialog_en)
    val wordLearned = LocalizedString(StringTag.WORD_LEARNED, R.string.word_learned, R.string.word_learned_ua, R.string.word_learned_en)
    val wordNotLearned = LocalizedString(StringTag.WORD_NOT_LEARNED, R.string.word_not_learned, R.string.word_not_learned_ua, R.string.word_not_learned_en)
    val wordDescription = LocalizedString(StringTag.WORD_DESCROPTION, R.string.word_description, R.string.word_description_ua, R.string.word_description_en)
    val translationDirection = LocalizedString(StringTag.TRANSLATION_DIRECTION, R.string.translation_direction, R.string.translation_direction_ua, R.string.translation_direction_en)
    val fromForeignToNative = LocalizedString(StringTag.FROM_FOREIGN_TO_NATIVE, R.string.from_en_to_ru, R.string.from_en_to_ru_ua, R.string.from_en_to_ru_en)
    val fromNativeToForeign = LocalizedString(StringTag.FROM_NATIVE_TO_FOREIGN, R.string.from_ru_to_en, R.string.from_ru_to_en_ua, R.string.from_ru_to_en_en)
    val darkMode = LocalizedString(StringTag.DARK_MODE, R.string.dark_mode, R.string.dark_mode_ua, R.string.dark_mode_en)
    val followSystemMode = LocalizedString(StringTag.FOLLOW_SYSTEM_MODE, R.string.follow_system_mode, R.string.follow_system_mode_ua, R.string.follow_system_mode_en)
    val appearance = LocalizedString(StringTag.APPEARANCE, R.string.appearance, R.string.appearance_ua, R.string.appearance_en)

//    val fromNative = LocalizedString(StringTag.FROM_NATIVE, R.string.from_russian, R.string.from_ukrainian_ua, R.string.from_english_en)
//    val toNative = LocalizedString(StringTag.FROM_NATIVE, R.string.to_russian, R.string.to_ukrainian_ua, R.string.to_english_en)
//    val fromUkrainian = LocalizedString(StringTag.FROM_UKRAINIAN, R.string.from_ukrainian, R.string.from_ukrainian_ua, R.string.from_ukrainian_en)
//    val fromEnglish = LocalizedString(StringTag.FROM_ENGLISH, R.string.from_english, R.string.from_english_ua, R.string.from_english_en)
//    val fromSwedish = LocalizedString(StringTag.FROM_SWEDISH, R.string.from_swedish, R.string.from_swedish_ua, R.string.from_swedish_en)
//    val toUkrainian = LocalizedString(StringTag.TO_UKRAINIAN, R.string.to_ukrainian, R.string.to_ukrainian_ua, R.string.to_ukrainian_en)
//    val toEnglish = LocalizedString(StringTag.TO_ENGLISH, R.string.to_english, R.string.to_english_ua, R.string.to_english_en)
//    val toSwedish = LocalizedString(StringTag.TO_SWEDISH, R.string.to_swedish, R.string.to_swedish_ua, R.string.to_swedish_en)

    val settings = LocalizedString(StringTag.SETTINGS, R.string.settings, R.string.settings_ua, R.string.settings_en)

    val chooseTime = LocalizedString(StringTag.CHOOSE_TIME, R.string.choose_time, R.string.choose_time_ua, R.string.choose_time_en)
    val notificationTitle = LocalizedString(StringTag.NOTIFICATION_TITLE, R.string.notification_title, R.string.notification_title_ua, R.string.notification_title_en)
    val notificationSubtitle = LocalizedString(StringTag.NOTIFICATION_SUBTITLE, R.string.notification_subtitle, R.string.notification_subtitle_ua, R.string.notification_subtitle_en)
    val remind = LocalizedString(StringTag.REMIND, R.string.remind, R.string.remind_ua, R.string.remind_en)
    val reminder = LocalizedString(StringTag.REMINDER, R.string.reminder, R.string.reminder_ua, R.string.reminder_en)
    val notificationScheduleTitle = LocalizedString(StringTag.NOTIFICATION_SCHEDULE_TITLE, R.string.notification_schedule_title, R.string.notification_schedule_title_ua, R.string.notification_schedule_title_en)
    val notificationSchedulePattern = LocalizedString(StringTag.NOTIFICATION_SCHEDULE_PATTERN, R.string.notification_schedule_pattern, R.string.notification_schedule_pattern_ua, R.string.notification_schedule_pattern_en)
    val notificationScheduleError = LocalizedString(StringTag.NOTIFICATION_SCHEDULE_ERROR, R.string.notification_schedule_error, R.string.notification_schedule_error_ua, R.string.notification_schedule_error_en)
    val btnNextText = LocalizedString(StringTag.BTN_NEXT_TEXT, R.string.btn_next_text, R.string.btn_next_text_ua, R.string.btn_next_text_en)

    val nativeLanguage = LocalizedString(StringTag.NATIVE_LANGUAGE, R.string.native_language, R.string.native_language, R.string.native_language_en)
    val learnedLanguege = LocalizedString(StringTag.NATIVE_LANGUAGE, R.string.learned_language, R.string.learned_language_ua, R.string.learned_language_en)
    val currentNativeLanguage = LocalizedString(StringTag.CURRENT_NATIVE_LANGUAGE, R.string.russian, R.string.ukrainian_ua, R.string.english)  // note here's a dif
    val nativeLanguageSettings = LocalizedString(StringTag.NATIVE_LANGUAGE_SETTINGS, R.string.native_language_settings, R.string.native_language_settings_ua, R.string.native_language_settings_en)
    val learnedLanguageSettings = LocalizedString(StringTag.LEARNED_LANGUAGE_SETTINGS, R.string.learned_language_settings, R.string.learned_language_settings_ua, R.string.learned_language_settings_en)
    val language = LocalizedString(StringTag.LANGUAGE, R.string.language, R.string.language_ua, R.string.language_en)

    val swedishWord = LocalizedString(StringTag.SWEDISH_WORD, R.string.foreign_word, R.string.foreign_word_ua, R.string.foreign_word_en)
    val russianTranslation = LocalizedString(StringTag.RUSSIAN_TRANSLATION, R.string.rus_word, R.string.rus_word_ua, R.string.rus_word_en)
    val englishTranslation = LocalizedString(StringTag.ENGLISH_TRANSLATION, R.string.eng_word, R.string.eng_word_ua, R.string.eng_word_en)
    val ukrainianTranslation = LocalizedString(StringTag.UKRAINIAN_TRANSLATION, R.string.ukr_word, R.string.ukr_word_ua, R.string.ukr_word_en)

}






