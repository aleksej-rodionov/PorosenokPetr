package space.rodionov.porosenokpetr.ui_compose

sealed class NavDestinations(val destination: String) {
    object ScreenCollection : NavDestinations("screen_collection")
    object ScreenWordlist : NavDestinations("screen_wordlist")
    object ScreenEditWord : NavDestinations("screen_edit_word")
}