package com.assesment.opentriviaquizapp.common.base

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    val ioCoroutine = CoroutineScope(Dispatchers.IO)
    val uiCoroutine = CoroutineScope(Dispatchers.Main)
}