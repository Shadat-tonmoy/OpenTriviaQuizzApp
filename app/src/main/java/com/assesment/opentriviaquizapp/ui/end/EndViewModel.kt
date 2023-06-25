package com.assesment.opentriviaquizapp.ui.end

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assesment.opentriviaquizapp.common.Operation
import com.assesment.opentriviaquizapp.common.base.BaseViewModel
import com.assesment.opentriviaquizapp.localData.CacheHelper
import com.assesment.opentriviaquizapp.model.QuizHistory
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


class EndViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        private const val TAG = "EndViewModel"
    }

    @Inject
    lateinit var cacheHelper: CacheHelper

    fun getEndScreenQuizDetails(id: String): LiveData<Operation<QuizHistory>> {
        val liveData = MutableLiveData<Operation<QuizHistory>>()

        ioCoroutine.launch {
            runCatching {
                val allQuizHistory = cacheHelper.getQuizHistory()
                val quizHistory = allQuizHistory.find { it.id == id }
                if (quizHistory != null) {
                    liveData.postValue(Operation.Success(quizHistory))
                } else {
                    liveData.postValue(Operation.Failure(Exception("No Quiz Data Found")))
                }
            }.onFailure {
                it.printStackTrace()
                liveData.postValue(Operation.Failure(Exception(it.message)))
            }
        }

        return liveData
    }
}