package com.github.donghune.data.local.dao

import androidx.room.*
import com.github.donghune.data.local.table.LatestSongEntity
import com.github.donghune.data.local.table.PopularitySongEntity
import com.github.donghune.data.local.table.SongEntity

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongPref(songEntity: SongEntity)

    @Query("SELECT * FROM songs WHERE title LIKE :keyword LIMIT :limit OFFSET :offset")
    suspend fun getSongListByKeyWord(keyword: String, offset: Int, limit: Int): List<SongEntity>

    @Query("SELECT * FROM songs WHERE id LIKE :id LIMIT :limit OFFSET :offset")
    suspend fun getSongListByNumber(id: Int, offset: Int, limit: Int): List<SongEntity>

    @Query("SELECT * FROM songs WHERE singing LIKE :singing LIMIT :limit OFFSET :offset")
    suspend fun getSongListBySinger(singing: String, offset: Int, limit: Int): List<SongEntity>

    @Query("SELECT * FROM songs WHERE singing LIKE :keyword or title LIKE :keyword LIMIT :limit OFFSET :offset")
    suspend fun getSongListByTitleWithSinger(
        keyword: String,
        offset: Int,
        limit: Int
    ): List<SongEntity>

    // pop

    @Query("SELECT * FROM popularity_songs order by rank")
    suspend fun getPopularitySongList(): List<PopularitySongEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularitySong(popularitySongEntity: PopularitySongEntity)

    @Query("DELETE FROM popularity_songs")
    suspend fun clearPopularitySongList()

    // latest

    @Query("SELECT * FROM latest_songs")
    suspend fun getLatestSongList(): List<LatestSongEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLatestSongPref(latestSongPrefs: LatestSongEntity)

    @Query("DELETE FROM latest_songs")
    suspend fun clearLatestSongList()

    @Query("SELECT * FROM songs WHERE id = :id")
    suspend fun getSongById(id: Int): SongEntity
}
