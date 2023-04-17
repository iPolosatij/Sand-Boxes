package com.digitallabstudio.sandboxes.presenter.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.digitallabstudio.sandboxes.R
import com.digitallabstudio.sandboxes.data.room.data.Bd_data
import com.digitallabstudio.sandboxes.databinding.ContactItemBinding
import com.digitallabstudio.sandboxes.domain.model.Item
import com.digitallabstudio.sandboxes.presenter.base.adapter.BaseItem
import com.digitallabstudio.sandboxes.presenter.base.adapter.BaseViewHolder
import com.squareup.picasso.Picasso

class ListItem (
    private val onClickItem: (Bd_data) -> Unit,
) : BaseItem<ContactItemBinding, Bd_data> {

    override fun isRelativeItem(item: Item): Boolean = item is Bd_data

    override fun getLayoutId(): Int = R.layout.contact_item


    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<ContactItemBinding, Bd_data> {
        val binding = ContactItemBinding.inflate(layoutInflater, parent, false)
        return ItemListViewHolder(binding, onClickItem)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<Bd_data>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<Bd_data>() {
        override fun areItemsTheSame(oldItem: Bd_data, newItem: Bd_data) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Bd_data, newItem: Bd_data) =
            oldItem.id == newItem.id
    }

    private class ItemListViewHolder(
        binding: ContactItemBinding,
        private val onClickItem: (Bd_data) -> Unit,
    ) : BaseViewHolder<ContactItemBinding, Bd_data>(binding) {

        override fun onBind(item: Bd_data) {
            super.onBind(item)
            setClickListener()
            setUpData()
        }
        fun setClickListener(){
            binding.apply {
                root.setOnClickListener {
                    if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                    onClickItem(item)
                }
            }
        }
        fun setUpData(){
            binding.apply {
                name.text = item.name
                lastName.text = item.lastName
                tel.text = item.tel
                Picasso.get().load(item.avatar).into(avatar)
            }
        }
    }
}