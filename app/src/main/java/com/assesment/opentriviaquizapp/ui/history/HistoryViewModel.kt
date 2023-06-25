package com.assesment.opentriviaquizapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assesment.opentriviaquizapp.common.Operation
import com.assesment.opentriviaquizapp.common.base.BaseViewModel
import com.assesment.opentriviaquizapp.localData.CacheHelper
import com.assesment.opentriviaquizapp.model.QuizHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        private const val TAG = "HistoryViewModel"
    }

    @Inject
    lateinit var cacheHelper: CacheHelper

    fun getQuizHistory(): LiveData<Operation<List<QuizHistory>>> {
        val liveData = MutableLiveData<Operation<List<QuizHistory>>>()

        ioCoroutine.launch {
            runCatching {
                val allQuizHistory = cacheHelper.getQuizHistory()
                liveData.postValue(Operation.Success(allQuizHistory))
            }.onFailure {
                it.printStackTrace()
                liveData.postValue(Operation.Failure(Exception(it.message)))
            }
        }

        return liveData
    }
}