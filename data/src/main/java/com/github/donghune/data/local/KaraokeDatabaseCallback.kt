package com.github.donghune.data.local

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class KaraokeDatabaseCallback(
    private val applicationContext: Context
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
//        Gson().fromJson(
//            applicationContext.resources.openRawResource(R.raw.default_data)
//                .bufferedReader(), Array<RawSongEntity>::class.java
//        ).forEach {
//            try {
//                db.execSQL(
//                    "INSERT INTO songs(id,title,singer) VALUES(" +
//                            "${it._id}," +
//                            "'${it.title.replace("'", "''")}'," +
//                            "'${it.singer.replace("'", "''")}'" +
//                            ")"
//                )
//            } catch (exception: Exception) {
//                Log.d(TAG, "onCreate: exception entity is $it")
//            }
//        }
    }

    companion object {
        private val TAG = KaraokeDatabaseCallback::class.java.simpleName
    }
}
