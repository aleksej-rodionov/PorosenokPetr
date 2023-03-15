package space.rodionov.porosenokpetr.feature_driller.presentation.base

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.porosenokpetr.feature_settings.domain.model.BaseModel
import space.rodionov.porosenokpetr.core.util.Constants.DEFAULT_INT
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.core.util.Constants.TAG_NATIVE_LANG

abstract class BaseAdapter(
    delegates: List<AdapterDelegate>,
    clickToAction: () -> Unit = {}
) : ListAdapter<BaseModel, BaseViewHolder>(BaseComparator()) {

    var modeBA = MODE_LIGHT
    open fun updateMode(mode: Int) {
        modeBA = mode
        notifyDataSetChanged() // then try to comment this out
    }

    var nativeLangBA = LANGUAGE_RU
    open fun updateNativeLang(lang: Int) {
        nativeLangBA = lang
        Log.d(TAG_NATIVE_LANG, "updateNativeLang: $lang")
        notifyDataSetChanged() // then try to comment this out
    }

//    var learnedLangBA = NATIVE_LANGUAGE_EN
//    open fun updateLearnedLang(lang: Int) {
//        learnedLangBA = lang
//        Log.d(TAG_DB_REFACTOR, "updateLearneLang: $lang")
//        notifyDataSetChanged() // then try to comment this out
//    }


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

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.setMode(modeBA)
            holder.setNativeLang(nativeLangBA)

            holder.bindPayload(getItem(position), holder, payloads[0] as MutableList<Any>)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.setMode(modeBA)
        holder.setNativeLang(nativeLangBA)

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












