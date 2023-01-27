package com.digitallabstudio.sandboxes.presenter.base.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.digitallabstudio.sandboxes.domain.model.Item

class MultiItemsAdapter(
    private val items: List<BaseItem<*, *>>,
) : ListAdapter<Item, BaseViewHolder<ViewBinding, Item>>(
    BaseItemDiffUtil(items)
) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, Item> {
        val inflater = LayoutInflater.from(parent.context)
        return items.find { it.getLayoutId() == viewType }
            ?.getViewHolder(inflater, parent)
            ?.let { it as BaseViewHolder<ViewBinding, Item> }
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, Item>, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, Item>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onBind(currentList[position], payloads)
        }
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<ViewBinding, Item>) {
        super.onViewDetachedFromWindow(holder)
        holder.onViewDetached()
    }

    override fun getItemViewType(position: Int): Int {
        try {
            val item = currentList[position]
            return items.find { it.isRelativeItem(item) }
                ?.getLayoutId()
                ?: throw IllegalArgumentException("View type not found: $item")
        } catch (ex: Exception) {
            Log.d("ArcTour_Log", "MultiAdapter error $ex")
            return 0
        }
    }
}
