package com.github.donghune.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.donghune.data.local.dao.PlayListDao
import com.github.donghune.data.local.dao.SongDao
import com.github.donghune.data.local.table.PlayListEntity
import com.github.donghune.data.local.table.SongEntity
import com.github.donghune.data.local.table.SongToPlayListEntity

@Database(
    entities = [PlayListEntity::class, SongToPlayListEntity::class, SongEntity::class],
    version = 1
)
abstract class KaraokeDatabase : RoomDatabase() {
    abstract fun playListDao(): PlayListDao

    abstract fun songDao(): SongDao

    companion object {
        private var instance: KaraokeDatabase? = null

        fun getInstance(applicationContext: Context): KaraokeDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    applicationContext,
                    KaraokeDatabase::class.java,
                    "database-karaoke"
                ).build()
            }
            return instance!!
        }

        private val TAG = KaraokeDatabase::class.java.simpleName
    }
}
