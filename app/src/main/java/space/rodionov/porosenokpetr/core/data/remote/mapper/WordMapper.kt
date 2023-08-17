package space.rodionov.porosenokpetr.core.data.remote.mapper

import space.rodionov.porosenokpetr.core.data.remote.model.WordDto
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE

fun WordDto.toWord(): Word {
    return Word(
        eng = eng,
        rus = rus,
        ukr = ukr,
        swe = swe,
        categoryName = catName,
        wordStatus = WORD_ACTIVE,
        id = 666 //todo check: does it come as String from Firestore?
    )
}