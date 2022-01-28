package space.rodionov.porosenokpetr.feature_driller.domain.models

data class Category(
    val name: String,
    val isCategoryActive: Boolean = true
): BaseModel