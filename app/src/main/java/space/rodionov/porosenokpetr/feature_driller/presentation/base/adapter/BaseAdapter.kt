package space.rodionov.porosenokpetr.feature_driller.presentation.base.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.feature_driller.Constants
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel

//abstract class BaseAdapter() : ListAdapter<BaseModel, BaseViewHolder>(BaseDiffUtil()) {
//
//    var isNightBaseAdapter = false
//    open fun updateMode(isNight: Boolean) {
//        isNightBaseAdapter = isNight
//    }
//
//    var nativeToForeign = false
//    open fun updateTranslationDirection(toForeign: Boolean) {
//        nativeToForeign = toForeign
//    }
//
//    lateinit var recyclerView: RecyclerView
//
//    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(recyclerView)
//        this.recyclerView = recyclerView
//    }
//
//    override open fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
//        return null
//    }
//
//
//    @SuppressLint("UNCHECKED_CAST")
//    override fun onBindViewHolder(
//        holder: BaseViewHolder,
//        position: Int,
//        payloads: MutableList<Any>
//    ) {
//        if (payloads.isEmpty()) {
//            super.onBindViewHolder(holder, position, payloads)
//        } else {
//            holder.bindPayload(getItem(position), holder, payloads[0] as MutableList<Any>)
//        }
//    }
//
//    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//        holder.setMode(isNightBaseAdapter)
//        holder.bind(getItem(position), holder)
//    }
//
//    fun getItemByPosition(position: Int) =
//        if (position != Constants.DEFAULT_INT && position < currentList.size) getItem(position) else null
//
//    fun getItems() = currentList
//
//    fun submitItem(item: BaseModel) {
//        synchronized(currentList) {
//            super.submitList(mutableListOf<BaseModel>().apply {
//                add(item)
//            })
//        }
//    }
//
//    override fun submitList(list: MutableList<BaseModel>?, commitCallback: Runnable?) {
//        synchronized(currentList) {
//            super.submitList(list, commitCallback)
//        }
//    }
//
//    fun isEmpty() = currentList.isEmpty()
//
//    override fun onCurrentListChanged(
//        previousList: MutableList<BaseModel>,
//        currentList: MutableList<BaseModel>
//    ) {
//        super.onCurrentListChanged(previousList, currentList)
//    }
//}


