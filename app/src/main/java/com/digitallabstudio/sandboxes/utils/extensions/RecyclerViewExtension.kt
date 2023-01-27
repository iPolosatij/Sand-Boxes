package com.digitallabstudio.sandboxes.utils.extensions

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.toListBottom(){
    this.post {
        this.adapter?.let {
            if (it.itemCount > 0)
                smoothScrollToPosition( it.itemCount - 1)
        }
    }
}