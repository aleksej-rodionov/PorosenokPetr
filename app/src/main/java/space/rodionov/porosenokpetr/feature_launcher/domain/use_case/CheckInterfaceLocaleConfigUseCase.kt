package space.rodionov.porosenokpetr.feature_launcher.domain.use_case

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateInterfaceLanguageUseCase
import space.rodionov.porosenokpetr.core.util.Language.Companion.LANGUAGE_RU

class CheckInterfaceLocaleConfigUseCase(
    private val context: Context,
    private val appCoroutineScope: CoroutineScope,
    private val updateInterfaceLanguageUseCase: UpdateInterfaceLanguageUseCase
) {

    operator fun invoke() {
        appCoroutineScope.launch(Dispatchers.Main) {
            val currentLanguageTag =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val currentAppLocales = context
                        .getSystemService(LocaleManager::class.java)
                        .applicationLocales
                    currentAppLocales[0]?.toLanguageTag()
                } else {
                    val currentAppLocales = AppCompatDelegate
                        .getApplicationLocales()
                    currentAppLocales[0]?.toLanguageTag()
                }

            updateInterfaceLanguageUseCase.invoke(currentLanguageTag ?: LANGUAGE_RU)
        }
    }
}