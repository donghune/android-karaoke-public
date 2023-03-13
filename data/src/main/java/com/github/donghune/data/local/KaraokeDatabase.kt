package com.github.donghune.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.donghune.data.local.dao.PlayListDao
import com.github.donghune.data.local.dao.SongDao
import com.github.donghune.data.local.table.*

@Database(
    entities = [PlayListEntity::class, SongToPlayListEntity::class, SongEntity::class, PopularitySongEntity::class, LatestSongEntity::class],
    version = 1
)
abstract class KaraokeDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun playListDao(): PlayListDao

    companion object {
        private var instance: KaraokeDatabase? = null

        fun getInstance(applicationContext: Context): KaraokeDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    applicationContext,
                    KaraokeDatabase::class.java,
                    "database-karaoke"
                ).addCallback(KaraokeDatabaseCallback(applicationContext))
                    .build()
            }
            return instance!!
        }

        private val TAG = KaraokeDatabase::class.java.simpleName
    }
}
