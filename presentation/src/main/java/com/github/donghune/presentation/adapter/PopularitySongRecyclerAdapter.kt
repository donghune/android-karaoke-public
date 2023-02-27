package com.github.donghune.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.donghune.presentation.BR
import com.github.donghune.presentation.databinding.ItemPopularitySongBinding
import com.github.donghune.presentation.dialog.GroupSelectDialogViewModel
import com.github.donghune.presentation.entity.PopularitySongModel

class PopularitySongRecyclerAdapter(
    private val groupSelectDialogViewModel: GroupSelectDialogViewModel
) : ListAdapter<PopularitySongModel, PopularitySongRecyclerAdapter.ViewHolder>(PopularitySongModel.diffCallback()) {

    class ViewHolder(
        private val groupSelectDialogViewModel: GroupSelectDialogViewModel,
        private val binding: ItemPopularitySongBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularitySongModel) {
            binding.setVariable(BR.song, item)
            binding.setVariable(BR.dialogViewModel, groupSelectDialogViewModel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            groupSelectDialogViewModel,
            ItemPopularitySongBinding.inflate(
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
