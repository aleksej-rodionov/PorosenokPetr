package space.rodionov.porosenokpetr.feature_driller.data.storage

interface Storage {

    fun setMode(isNight: Boolean)
    fun getMode(): Boolean
}