package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import space.rodionov.porosenokpetr.feature_driller.domain.models.CatWithWords
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_LIGHT

data class CollectionState(
    val catsWithWords: List<CatWithWords> = emptyList()
) {
}