package space.rodionov.porosenokpetr.feature_driller.presentation.base

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.DEFAULT_INT
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MILLIS_IN_NINE_HOURS
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_SETTINGS

abstract class BaseAdapter(
    delegates: List<AdapterDelegate>,
    clickToAction: () -> Unit = {}
) : ListAdapter<BaseModel, BaseViewHolder>(BaseComparator()) {

    var transDirBA = false
    open fun setTransDir(nativeToForeign: Boolean) {
        transDirBA = nativeToForeign
//        notifyDataSetChanged()
    }

    var modeBA = MODE_LIGHT
    open fun updateMode(mode: Int) {
        modeBA = mode
//        notifyDataSetChanged()
    }

    var followSystemModeBA = false
    open fun updateFollowSystemModeBA(follow: Boolean) {
        followSystemModeBA = follow
//        notifyDataSetChanged()
    }

    var notifyBA = false
    open fun updateNotify(notify: Boolean) {
        notifyBA = notify
//        notifyDataSetChanged()
    }

    var notificationTimeBA = MILLIS_IN_NINE_HOURS
    open fun updateNotificationTime(millisFromDayBeginning: Long) {
        notificationTimeBA = millisFromDayBeginning
//        notifyDataSetChanged()
    }


    //=========================MAIN BOILERPLATE==================
    private val delegateManager = AdapterDelegateManager()
    lateinit var recyclerView: RecyclerView

    init {
        delegates.forEach {
            delegateManager.addDelegate(it)
        }
        // here add default empty/loaders delegates
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return delegateManager.getDelegate(viewType).onCreateViewHolder(parent)
    }

    @SuppressLint("UNCHECKED_CAST")
    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        Log.d(TAG_SETTINGS, "onBindViewHolder: with payloads CALLED")

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.setTranslationDirection(transDirBA)
            holder.setMode(modeBA)
            holder.setFollowSystemMode(followSystemModeBA)
            holder.setNotify(notifyBA)
            holder.setNotificationTime(notificationTimeBA)

            holder.bindPayload(getItem(position), holder, payloads[0] as MutableList<Any>)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.setTranslationDirection(transDirBA)
        holder.setMode(modeBA)
        holder.setFollowSystemMode(followSystemModeBA)
        holder.setNotify(notifyBA)
        holder.setNotificationTime(notificationTimeBA)

        holder.bind(getItem(position), holder)
    }

    override fun getItemViewType(position: Int): Int {
        return delegateManager.getItemViewType(getItem(position))
    }

    fun getItemByPosition(position: Int) =
        if (position != DEFAULT_INT && position < currentList.size) getItem(position) else null

    fun getItems() = currentList

    fun submitItem(item: BaseModel) {
        synchronized(currentList) {
            super.submitList(mutableListOf<BaseModel>().apply {
                add(item)
            })
        }
    }

    override fun submitList(list: MutableList<BaseModel>?, commitCallback: Runnable?) {
        synchronized(currentList) {
            super.submitList(list, commitCallback)
        }
    }

    fun isEmpty() = currentList.isEmpty()

    override fun onCurrentListChanged(
        previousList: MutableList<BaseModel>,
        currentList: MutableList<BaseModel>
    ) {
        super.onCurrentListChanged(previousList, currentList)
    }
}












