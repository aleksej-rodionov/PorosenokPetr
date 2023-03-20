package space.rodionov.porosenokpetr.core.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*

private const val TAG = "SwedishSpeaker"

class SwedishSpeaker(
    private val context: Context
) : TextToSpeech.OnInitListener {

    val googleTtsPackage = "com.google.android.tts"

    private val textToSpeech = TextToSpeech(
        context,
        this,
        googleTtsPackage
    )

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            try {
                textToSpeech.language = Locale("sv", "SE")
                Log.d(TAG, "onInit: successful")
            } catch (e: Exception) {
                Log.d(TAG, "onInit: ${e.message}")
            }
        } else Log.d(TAG, "onInit: Language initialization failed")
    }

    fun speakWord(word: String) {
        textToSpeech.apply {
            stop()
            speak(word, TextToSpeech.QUEUE_FLUSH, null)
        }
    }
}