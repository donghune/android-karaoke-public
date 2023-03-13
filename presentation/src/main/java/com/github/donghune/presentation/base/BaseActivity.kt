package com.github.donghune.presentation.base

import androidx.appcompat.app.AppCompatActivity
import com.github.donghune.presentation.view.LoadingDialog

abstract class BaseActivity : AppCompatActivity() {
    val loadingDialog by lazy { LoadingDialog(this) }
}
