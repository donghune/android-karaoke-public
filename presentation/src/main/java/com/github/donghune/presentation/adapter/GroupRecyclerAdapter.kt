package com.github.donghune.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.donghune.presentation.BR
import com.github.donghune.presentation.databinding.ItemGroupBinding
import com.github.donghune.presentation.entity.GroupModel

class GroupRecyclerAdapter : ListAdapter<GroupModel, GroupRecyclerAdapter.ViewHolder>(
    GroupModel.diffCallback()
) {

    class ViewHolder(
        private val binding: ItemGroupBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroupModel) {
            binding.setVariable(BR.group, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}