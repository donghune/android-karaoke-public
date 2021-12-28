package com.github.donghune.data.local

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.donghune.data.R
import com.github.donghune.data.local.entity.RawSongEntity
import com.google.gson.Gson

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
//                    "INSERT INTO songs(id,title,singing) VALUES(" +
//                            "${it._id}," +
//                            "'${it.title.replace("'", "''")}'," +
//                            "'${it.singing.replace("'", "''")}'" +
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