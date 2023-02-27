package com.github.donghune.data.local.dao

import androidx.room.*
import com.github.donghune.data.local.table.LatestSongPref
import com.github.donghune.data.local.table.PopularitySongPref
import com.github.donghune.data.local.table.SongPref

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongPref(songPref: SongPref)

    @Query("SELECT * FROM songs WHERE title LIKE :keyword LIMIT :limit OFFSET :offset")
    suspend fun getSongListByKeyWord(keyword: String, offset: Int, limit: Int): List<SongPref>

    @Query("SELECT * FROM songs WHERE id LIKE :id LIMIT :limit OFFSET :offset")
    suspend fun getSongListByNumber(id: Int, offset: Int, limit: Int): List<SongPref>

    @Query("SELECT * FROM songs WHERE singing LIKE :singing LIMIT :limit OFFSET :offset")
    suspend fun getSongListBySinger(singing: String, offset: Int, limit: Int): List<SongPref>

    @Query("SELECT * FROM songs WHERE singing LIKE :keyword or title LIKE :keyword LIMIT :limit OFFSET :offset")
    suspend fun getSongListByTitleWithSinger(keyword: String, offset: Int, limit: Int): List<SongPref>

    // pop

    @Query("SELECT * FROM popularity_songs order by rank")
    suspend fun getPopularitySongList(): List<PopularitySongPref>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularitySong(popularitySongPref: PopularitySongPref)

    @Query("DELETE FROM popularity_songs")
    suspend fun clearPopularitySongList()

    // latest

    @Query("SELECT * FROM latest_songs")
    suspend fun getLatestSongList(): List<LatestSongPref>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLatestSongPref(latestSongPrefs: LatestSongPref)

    @Query("DELETE FROM latest_songs")
    suspend fun clearLatestSongList()
}
