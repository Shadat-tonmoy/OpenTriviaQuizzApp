package com.assesment.opentriviaquizapp.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assesment.opentriviaquizapp.common.Operation
import com.assesment.opentriviaquizapp.common.base.BaseViewModel
import com.assesment.opentriviaquizapp.model.Question
import com.assesment.opentriviaquizapp.network.apiHanlder.QuestionApiHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        private const val TAG = "QuizViewModel"
    }

    @Inject
    lateinit var questionApiHandler: QuestionApiHandler

    fun fetchQuestionList(): LiveData<Operation<List<Question>>> {
        val liveData = MutableLiveData<Operation<List<Question>>>()

        ioCoroutine.launch {
            runCatching {
                val questionList = questionApiHandler.fetchAllQuestions()
                liveData.postValue(Operation.Success(questionList))
            }.onFailure {
                it.printStackTrace()
                liveData.postValue(Operation.Failure(Exception(it.message)))
            }
        }
        return liveData

    }

}