package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.util.ForeignSpeaker

class SpeakWordUseCase(
    private val speaker: ForeignSpeaker
) {

    operator fun invoke(word: String) {
        speaker.speakWord(word)
    }
}