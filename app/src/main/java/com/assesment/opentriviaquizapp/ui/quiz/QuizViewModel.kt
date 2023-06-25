package com.assesment.opentriviaquizapp.ui.quiz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assesment.opentriviaquizapp.common.Operation
import com.assesment.opentriviaquizapp.common.base.BaseViewModel
import com.assesment.opentriviaquizapp.common.constants.Constants
import com.assesment.opentriviaquizapp.common.constants.Constants.CORRECT
import com.assesment.opentriviaquizapp.common.constants.Constants.SINGLE_QUESTION_TIME
import com.assesment.opentriviaquizapp.model.Question
import com.assesment.opentriviaquizapp.network.apiHanlder.QuestionApiHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        private const val TAG = "QuizViewModel"
    }

    @Inject
    lateinit var questionApiHandler: QuestionApiHandler

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private val questionTimerLiveData = MutableLiveData<Long>()
    private var currentQuestionLeftTime = SINGLE_QUESTION_TIME
    private var answerStack = mutableListOf<Int>()
    var currentScore = 0

    fun fetchQuestionList(): LiveData<Operation<List<Question>>> {
        val liveData = MutableLiveData<Operation<List<Question>>>()

        ioCoroutine.launch {
            runCatching {
                val questionList = questionApiHandler.fetchAllQuestions()
                Log.e(TAG, "fetchQuestionList: questionList : $questionList")
                liveData.postValue(Operation.Success(questionList))
            }.onFailure {
                it.printStackTrace()
                liveData.postValue(Operation.Failure(Exception(it.message)))
            }
        }
        return liveData

    }

    fun startCurrentQuestionTimer() {
        timer?.cancel()
        timer = Timer()
        initTimerTask()
        currentQuestionLeftTime = SINGLE_QUESTION_TIME
        timer?.scheduleAtFixedRate(
            timerTask,
            0,
            1000
        )
    }

    private fun initTimerTask() {
        timerTask?.cancel()
        timerTask = object : TimerTask() {
            override fun run() {
                currentQuestionLeftTime -= 1000
                if (isCurrentQuestionTimeFinished()) currentQuestionLeftTime = SINGLE_QUESTION_TIME
                questionTimerLiveData.postValue(currentQuestionLeftTime)
            }
        }
    }

    fun isCurrentQuestionTimeFinished(): Boolean {
        return currentQuestionLeftTime <= 0
    }

    fun getQuestionTimerLiveData(): LiveData<Long> {
        return questionTimerLiveData
    }

    fun getRemainingTimeProgress(): Float {
        return (((currentQuestionLeftTime.toFloat() / SINGLE_QUESTION_TIME.toFloat()) * 100).toInt()).toFloat()
    }

    fun addAnswerFlagToStack(answerFlag: Int) {
        answerStack.add(answerFlag)
        if (answerFlag == CORRECT) currentScore++
        else currentScore--
        currentScore = maxOf(currentScore, 0)
    }

    fun getAnswerFlagList(): List<Int> {
        return answerStack
    }

}