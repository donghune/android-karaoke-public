package com.github.donghune.presentation.entity

import androidx.recyclerview.widget.DiffUtil
import com.github.donghune.domain.entity.Song

data class SongModel(
    val id: Int,
    val title: String,
    val singer: String
) {
    companion object {
        fun diffCallback() = object : DiffUtil.ItemCallback<SongModel>() {
            override fun areItemsTheSame(oldItem: SongModel, newItem: SongModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SongModel, newItem: SongModel): Boolean =
                oldItem == newItem
        }

        operator fun invoke(entity: Song) = SongModel(entity.id, entity.title, entity.singer)
    }

    val karaokeNumber = String.format("%05d", id)
}

fun SongModel.toSong(): Song {
    return Song(id, title, singer)
}

fun Song.toSongModel(): SongModel {
    return SongModel(id, title, singer)
}
