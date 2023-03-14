package com.github.donghune.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.donghune.databinding.ItemSongBinding
import com.github.donghune.presentation.entity.SongModel

class SongRecyclerAdapter(
    private val openOnClickListener: (SongModel) -> Unit
) : ListAdapter<SongModel, SongRecyclerAdapter.ViewHolder>(SongModel.diffCallback()) {

    class ViewHolder(
        private val binding: ItemSongBinding,
        private val openOnClickListener: (SongModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SongModel) {
            binding.textNumber.text = item.karaokeNumber
            binding.textSinger.text = item.singer
            binding.textTitle.text = item.title
            binding.imageOption.setOnClickListener {
                openOnClickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            openOnClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
