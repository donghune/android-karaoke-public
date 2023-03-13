package com.github.donghune.presentation.entity

import androidx.recyclerview.widget.DiffUtil
import com.github.donghune.domain.entity.PlayList

data class PlayListModel(
    val id: Int,
    val name: String,
    val songNumbers: List<Int>
) {

    companion object {
        fun diffCallback() = object : DiffUtil.ItemCallback<PlayListModel>() {
            override fun areItemsTheSame(oldItem: PlayListModel, newItem: PlayListModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PlayListModel, newItem: PlayListModel): Boolean =
                oldItem == newItem
        }
    }
}

fun PlayList.toPlayListModel(): PlayListModel {
    return PlayListModel(id, name, songNumbers)
}

fun PlayListModel.toPlayList(): PlayList {
    return PlayList(id, name, songNumbers)
}
