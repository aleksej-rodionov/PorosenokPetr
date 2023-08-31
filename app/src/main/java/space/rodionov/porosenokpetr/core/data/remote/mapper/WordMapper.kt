package space.rodionov.porosenokpetr.core.data.remote.mapper

import android.util.Log
import space.rodionov.porosenokpetr.core.data.local.entity.WordRaw
import space.rodionov.porosenokpetr.core.data.remote.model.WordDto

private const val TAG = "WordMappingTAG"

fun WordDto.toWordRaw(): WordRaw {
    return try {
        WordRaw(
            catName = catName,
            eng = eng,
            rus = rus,
            ukr = ukr,
            swe = swe,
            examples = examples ?: emptyList()
        )
    } catch (e: Exception) {
        Log.d(TAG, "toWordRaw: ERROR while mapping word $this")
        Log.d(TAG, "toWordRaw: ERROR msg = ${e.message}")
        throw e
    }
}