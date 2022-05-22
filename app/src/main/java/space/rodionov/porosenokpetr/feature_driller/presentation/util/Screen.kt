package space.rodionov.porosenokpetr.feature_driller.presentation.util

sealed class Screen(val route: String) {
    object CollectionScreen: Screen("collection_screen")
    object WordlistScreen: Screen("wordlist_screen")
    object EditWordScreen: Screen("edit_word_screen")
}
