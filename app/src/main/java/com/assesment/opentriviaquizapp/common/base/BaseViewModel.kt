package com.assesment.opentriviaquizapp.common.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {
    val ioCoroutine = CoroutineScope(Dispatchers.IO)
    val uiCoroutine = CoroutineScope(Dispatchers.Main)


}