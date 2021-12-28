package com.github.donghune.karaoke

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.donghune.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        finish()
        MainActivity.startActivity(this)
    }
}