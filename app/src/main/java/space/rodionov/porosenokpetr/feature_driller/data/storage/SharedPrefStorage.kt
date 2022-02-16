package space.rodionov.porosenokpetr.feature_driller.data.storage

interface Storage {

    fun getMode(): Int
    fun setMode(mode: Int)

    fun getFollowSystemMode(): Boolean
    fun setFollowSystemMode(follow: Boolean)
}