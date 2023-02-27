package com.github.donghune.presentation.entity

import androidx.recyclerview.widget.DiffUtil
import com.github.donghune.domain.entity.PopularitySongEntity

data class PopularitySongModel(
    val rank: Int,
    val id: Int,
    val title: String,
    val singing: String
) {
    fun toKaraokeNumber(): String {
        return String.format("%05d", id)
    }

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

        operator fun invoke(entity: PopularitySongEntity) = entity.toPopularitySongModel()
    }
}

fun PopularitySongModel.toPopularitySongEntity(): PopularitySongEntity {
    return PopularitySongEntity(rank, id, title, singing)
}

fun PopularitySongEntity.toPopularitySongModel(): PopularitySongModel {
    return PopularitySongModel(rank, id, title, singing)
}
