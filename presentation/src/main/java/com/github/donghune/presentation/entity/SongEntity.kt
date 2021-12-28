package com.github.donghune.presentation.entity

import androidx.recyclerview.widget.DiffUtil
import com.github.donghune.domain.entity.SongEntity

data class SongModel(
    val id: Int,
    val title: String,
    val singer: String,
) {
    companion object {
        fun diffCallback() = object : DiffUtil.ItemCallback<SongModel>() {
            override fun areItemsTheSame(oldItem: SongModel, newItem: SongModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SongModel, newItem: SongModel): Boolean =
                oldItem == newItem
        }

        operator fun invoke(entity: SongEntity) = SongModel(entity.id, entity.title, entity.singer)
    }

    fun toKaraokeNumber(): String {
        return String.format("%05d", id)
    }
}

fun SongModel.SongEntity(): SongEntity {
    return SongEntity(id, title, singer)
}