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

    val top800words = LocalizedString(StringTag.TOP_800_WORDS, R.string.top_800_words, R.string.top_800_words_ua)
    val top200verbs = LocalizedString(StringTag.TOP_200_VERBS, R.string.top_200_verbs, R.string.top_200_verbs_ua)
    val usualWords = LocalizedString(StringTag.USUAL_WORDS, R.string.usual_words, R.string.usual_words_ua)
    val usualVerbs = LocalizedString(StringTag.USUAL_VERBS, R.string.usual_verbs, R.string.usual_verbs_ua)
    val phrasalVerbs = LocalizedString(StringTag.PHRASAL_VERBS, R.string.phrasal_verbs, R.string.phrasal_verbs_ua)
    val phrases = LocalizedString(StringTag.PHRASES, R.string.phrases, R.string.phrases_ua)

    val itemCount = LocalizedString(StringTag.ITEM_COUNT, R.string.item_count, R.string.item_count_ua)
    val currentPosition = LocalizedString(StringTag.CURRENT_POSITION, R.string.current_position, R.string.current_position_ua)
    val onCardAppeared = LocalizedString(StringTag.ON_CARD_APPEARED, R.string.on_card_appeared, R.string.on_card_appeared_ua)
    val onCardDisappeared = LocalizedString(StringTag.ON_CARD_DISAPPEARED, R.string.on_card_disappeared, R.string.on_card_disappeared_ua)

    val activeCategories = LocalizedString(StringTag.ACTIVE_CATEGORIES, R.string.active_categories, R.string.active_categories_ua)
    val activateAllCategories = LocalizedString(StringTag.ACTIVATE_ALL_CATEGORIES, R.string.active_all_categories, R.string.active_categories_ua)
    val cannotTurnAllCatsOff = LocalizedString(StringTag.CANNOT_TURN_ALL_CATS_OFF, R.string.cannot_turn_all_cats_off, R.string.cannot_turn_all_cats_off_ua)

    val collection = LocalizedString(StringTag.COLLECTION, R.string.collection, R.string.collection_ua)
    val searchWord = LocalizedString(StringTag.SEARCH_WORD, R.string.search_word, R.string.search_word_ua)
    val searchAmongAllWords = LocalizedString(StringTag.SEARCH_AMONG_ALL_WORDS, R.string.search_among_all_words, R.string.search_among_all_words_ua)
    val searchIn = LocalizedString(StringTag.SEARCH_IN, R.string.search_in, R.string.search_in_ua)
    val percentage = LocalizedString(StringTag.PERCENTAGE, R.string.percentage, R.string.percentage_ua)

    val category = LocalizedString(StringTag.CATEGORY, R.string.category, R.string.category_ua)
    val wordInDialog = LocalizedString(StringTag.WORD_IN_DIALOG, R.string.word_in_dialog, R.string.word_in_dialog_ua)
    val wordLearned = LocalizedString(StringTag.WORD_LEARNED, R.string.word_learned, R.string.word_learned_ua)
    val wordNotLearned = LocalizedString(StringTag.WORD_NOT_LEARNED, R.string.word_not_learned, R.string.word_not_learned_ua)
    val wordDescription = LocalizedString(StringTag.WORD_DESCROPTION, R.string.word_description, R.string.word_description_ua)
    val translationDirection = LocalizedString(StringTag.TRANSLATION_DIRECTION, R.string.translation_direction, R.string.translation_direction_ua)
    val fromForeignToNative = LocalizedString(StringTag.FROM_FOREIGN_TO_NATIVE, R.string.from_en_to_ru, R.string.from_en_to_ru_ua)
    val fromNativeToForeign = LocalizedString(StringTag.FROM_NATIVE_TO_FOREIGN, R.string.from_ru_to_en, R.string.from_ru_to_en_ua)
    val darkMode = LocalizedString(StringTag.DARK_MODE, R.string.dark_mode, R.string.dark_mode_ua)
    val followSystemMode = LocalizedString(StringTag.FOLLOW_SYSTEM_MODE, R.string.follow_system_mode, R.string.follow_system_mode_ua)
    val appearance = LocalizedString(StringTag.APPEARANCE, R.string.appearance, R.string.appearance_ua)

    val settings = LocalizedString(StringTag.SETTINGS, R.string.settings, R.string.settings_ua)

    val chooseTime = LocalizedString(StringTag.CHOOSE_TIME, R.string.choose_time, R.string.choose_time_ua)
    val notificationTitle = LocalizedString(StringTag.NOTIFICATION_TITLE, R.string.notification_title, R.string.notification_title_ua)
    val notificationSubtitle = LocalizedString(StringTag.NOTIFICATION_SUBTITLE, R.string.notification_subtitle, R.string.notification_subtitle_ua)
    val remind = LocalizedString(StringTag.REMIND, R.string.remind, R.string.remind_ua)
    val reminder = LocalizedString(StringTag.REMINDER, R.string.reminder, R.string.reminder_ua)
    val notificationScheduleTitle = LocalizedString(StringTag.NOTIFICATION_SCHEDULE_TITLE, R.string.notification_schedule_title, R.string.notification_schedule_title_ua)
    val notificationSchedulePattern = LocalizedString(StringTag.NOTIFICATION_SCHEDULE_PATTERN, R.string.notification_schedule_pattern, R.string.notification_schedule_pattern_ua)
    val notificationScheduleError = LocalizedString(StringTag.NOTIFICATION_SCHEDULE_ERROR, R.string.notification_schedule_error, R.string.notification_schedule_error_ua)
    val btnNextText = LocalizedString(StringTag.BTN_NEXT_TEXT, R.string.btn_next_text, R.string.btn_next_text_ua)

    val nativeLanguage = LocalizedString(StringTag.NATIVE_LANGUAGE, R.string.russian, R.string.ukrainian_ua) // note here's a dif
    val currentNativeLanguage = LocalizedString(StringTag.CURRENT_NATIVE_LANGUAGE, R.string.russian, R.string.ukrainian_ua)

}






