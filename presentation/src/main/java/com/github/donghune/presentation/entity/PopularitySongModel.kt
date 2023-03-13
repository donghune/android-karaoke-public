package com.github.donghune.presentation.entity

import androidx.recyclerview.widget.DiffUtil
import com.github.donghune.domain.entity.PopularitySong

data class PopularitySongModel(
    val rank: Int,
    val id: Int,
    val title: String,
    val singing: String
) {
    val karaokeNumber = String.format("%05d", id)

    companion object {
        fun diffCallback() = object : DiffUtil.ItemCallback<PopularitySongModel>() {
            override fun areItemsTheSame(
                oldItem: PopularitySongModel,
                newItem: PopularitySongModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PopularitySongModel,
                newItem: PopularitySongModel
            ): Boolean = oldItem == newItem
        }

        operator fun invoke(entity: PopularitySong) = entity.toPopularitySongModel()
    }
}

fun PopularitySongModel.toPopularitySong(): PopularitySong {
    return PopularitySong(rank, id, title, singing)
}

fun PopularitySong.toPopularitySongModel(): PopularitySongModel {
    return PopularitySongModel(rank, id, title, singing)
}
