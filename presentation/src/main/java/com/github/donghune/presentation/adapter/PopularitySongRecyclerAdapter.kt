package com.github.donghune.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.donghune.databinding.ItemSongBinding
import com.github.donghune.presentation.entity.PopularitySongModel

class PopularitySongRecyclerAdapter(
    private val openOnClickListener: (Int) -> Unit
) : ListAdapter<PopularitySongModel, PopularitySongRecyclerAdapter.ViewHolder>(PopularitySongModel.diffCallback()) {

    class ViewHolder(
        private val binding: ItemSongBinding,
        private val openOnClickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularitySongModel) {
            binding.textNumber.text = item.karaokeNumber
            binding.textSinger.text = item.singing
            binding.textTitle.text = item.title
            binding.imageOption.setOnClickListener {
                openOnClickListener(item.id)
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
