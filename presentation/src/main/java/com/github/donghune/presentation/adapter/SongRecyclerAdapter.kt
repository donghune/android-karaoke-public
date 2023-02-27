package com.github.donghune.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.donghune.presentation.BR
import com.github.donghune.presentation.databinding.ItemSongBinding
import com.github.donghune.presentation.dialog.GroupSelectDialogViewModel
import com.github.donghune.presentation.entity.SongModel

class SongRecyclerAdapter(
    private val dialogViewModel: GroupSelectDialogViewModel
) :
    ListAdapter<SongModel, SongRecyclerAdapter.ViewHolder>(SongModel.diffCallback()) {

    class ViewHolder(
        private val groupSelectDialogViewModel: GroupSelectDialogViewModel,
        private val binding: ItemSongBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SongModel) {
            binding.setVariable(BR.song, item)
            binding.setVariable(BR.dialogViewModel, groupSelectDialogViewModel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            dialogViewModel,
            ItemSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val TAG = SongRecyclerAdapter::class.java.simpleName
    }
}
