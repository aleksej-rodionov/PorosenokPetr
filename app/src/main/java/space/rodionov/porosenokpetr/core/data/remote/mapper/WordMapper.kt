package space.rodionov.porosenokpetr.core.data.remote.mapper

import space.rodionov.porosenokpetr.core.data.local.entity.WordRaw
import space.rodionov.porosenokpetr.core.data.remote.model.WordDto

fun WordDto.toWordRaw(): WordRaw {
    return WordRaw(
        catName = catName,
        eng = eng,
        rus = rus,
        ukr = ukr,
        swe = swe,
        examples = examples
    )
}