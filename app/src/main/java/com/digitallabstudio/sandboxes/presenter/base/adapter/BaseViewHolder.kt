package com.digitallabstudio.sandboxes.presenter.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.digitallabstudio.sandboxes.domain.model.Item

abstract class BaseViewHolder<out V : ViewBinding, I : Item>(

    val binding: V
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: I

    open fun onBind(item: I) {
        this.item = item
    }

    open fun onBind(item: I, payloads: List<Any>) {
        this.item = item
    }

    open fun onViewDetached() = Unit
}