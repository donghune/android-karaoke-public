package com.github.donghune.data.local

import android.content.Context
import com.github.donghune.data.local.dao.GroupDao
import com.github.donghune.data.local.dao.PlayListDao
import com.github.donghune.data.local.dao.SongDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): KaraokeDatabase {
        return KaraokeDatabase.getInstance(applicationContext)
    }

    @Provides
    fun provideSongDao(database: KaraokeDatabase): SongDao {
        return database.songDao()
    }

    @Provides
    fun provideGroupDao(database: KaraokeDatabase): GroupDao {
        return database.groupDao()
    }

    @Provides
    fun providePlayListDao(database: KaraokeDatabase): PlayListDao {
        return database.playListDao()
    }
}
