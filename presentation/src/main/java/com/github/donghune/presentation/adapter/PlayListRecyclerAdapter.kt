package com.github.donghune.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.donghune.databinding.ItemPlayListBinding
import com.github.donghune.presentation.entity.PlayListModel

class PlayListRecyclerAdapter(
    private val onClickListener: (PlayListModel) -> Unit
) : ListAdapter<PlayListModel, PlayListRecyclerAdapter.ViewHolder>(
    PlayListModel.diffCallback()
) {

    class ViewHolder(
        private val binding: ItemPlayListBinding,
        private val onClickListener: (PlayListModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlayListModel) {
            binding.textPlayListName.text = item.name
            binding.textSavedSongCount.text = "추가되어 있는 애창곡 총 %d 곡".format(item.songNumbers.count())
            binding.root.setOnClickListener { onClickListener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPlayListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
