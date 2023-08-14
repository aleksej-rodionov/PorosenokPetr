package space.rodionov.porosenokpetr.core.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

private const val TAG = "ForeignSpeaker"

class ForeignSpeaker(
    private val context: Context
) : TextToSpeech.OnInitListener {

    private val textToSpeech = TextToSpeech(
        context,
        this
    )

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            try {
                textToSpeech.language = Locale.US
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