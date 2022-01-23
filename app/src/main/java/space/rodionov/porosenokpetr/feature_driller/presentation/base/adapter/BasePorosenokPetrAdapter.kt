package space.rodionov.porosenokpetr.feature_driller.presentation.base.adapter

class BasePorosenokPetrAdapter(
    changeMode: (Boolean) -> Unit = {}
) : BaseAdapter(
    emptyList(),
    switchMode = changeMode
)