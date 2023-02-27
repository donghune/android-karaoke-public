package com.github.donghune.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.donghune.data.local.table.GroupPref

@Dao
interface GroupDao {

    @Query("SELECT * FROM groups")
    suspend fun getGroupList(): List<GroupPref>

    @Query("SELECT * FROM groups WHERE id = :groupId")
    suspend fun getGroup(groupId: Int): GroupPref

    @Insert
    suspend fun addGroup(groupPref: GroupPref)

    @Delete
    suspend fun removeGroup(groupPref: GroupPref)
}
