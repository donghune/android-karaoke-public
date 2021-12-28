package com.github.donghune.presentation.entity

import androidx.recyclerview.widget.DiffUtil
import com.github.donghune.domain.entity.GroupEntity

data class GroupModel(
    val id: Int,
    val name: String,
    val songNumbers: List<Int>,
) {

    companion object {
        fun diffCallback() = object : DiffUtil.ItemCallback<GroupModel>() {
            override fun areItemsTheSame(oldItem: GroupModel, newItem: GroupModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GroupModel, newItem: GroupModel): Boolean =
                oldItem == newItem
        }
    }
}

fun GroupEntity.toGroupModel(): GroupModel {
    return GroupModel(id, name, listOf())
}

fun GroupModel.toGroupEntity(): GroupEntity {
    return GroupEntity(id, name, songNumbers)
}