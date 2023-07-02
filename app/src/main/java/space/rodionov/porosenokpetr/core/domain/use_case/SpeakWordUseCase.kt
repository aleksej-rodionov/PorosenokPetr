package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.util.SwedishSpeaker

class SpeakWordUseCase(
    private val speaker: SwedishSpeaker
) {

    operator fun invoke(word: String) {
        speaker.speakWord(word)
    }
}