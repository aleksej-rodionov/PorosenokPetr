package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateInterfaceLanguageUseCase

class SetInterfaceLocaleConfigUseCase(
    private val context: Context,
    private val appCoroutineScope: CoroutineScope,
    private val updateInterfaceLanguageUseCase: UpdateInterfaceLanguageUseCase
) {

    operator fun invoke(languageTag: String) {
        appCoroutineScope.launch(Dispatchers.Main) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.getSystemService(LocaleManager::class.java)
                    .applicationLocales = LocaleList.forLanguageTags(languageTag)
            } else {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(languageTag)
                )
            }

            updateInterfaceLanguageUseCase.invoke(languageTag) //todo check w debugger if triggered
        }
    }
}