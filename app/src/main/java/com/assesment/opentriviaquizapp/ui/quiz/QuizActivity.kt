package com.assesment.opentriviaquizapp.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.assesment.opentriviaquizapp.R
import com.assesment.opentriviaquizapp.common.Operation
import com.assesment.opentriviaquizapp.common.base.BaseActivity
import com.assesment.opentriviaquizapp.databinding.ActivityQuizBinding
import com.assesment.opentriviaquizapp.model.Question
import com.assesment.opentriviaquizapp.ui.helpers.getTimeDurationString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : BaseActivity() {


    companion object {
        private const val TAG = "QuizActivity"
    }

    private val viewModel: QuizViewModel by viewModels()

    private val viewBinding by lazy { ActivityQuizBinding.inflate(LayoutInflater.from(this)) }
    private val quizListAdapter by lazy { QuizListAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initUI()
        fetchAllQuestions()
    }

    private fun initUI() {
        setContentView(viewBinding.root)
        with(viewBinding) {
            questionList.adapter = quizListAdapter
            questionList.isUserInputEnabled = false
            questionList.setPageTransformer { page, position ->
                updateCurrentQuestionLabel(position.toInt())
                startQuestionTimer()
            }
        }
        observeCurrentQuestionRemainingTime()
    }

    private fun updateCurrentQuestionLabel(position: Int) {
        viewBinding.questionCountLabel.text = getString(
            R.string.question_count_placeholder,
            "${position + 1}",
            "${quizListAdapter.itemCount}"
        )
    }

    private fun startQuestionTimer(){
        viewModel.startCurrentQuestionTimer()
    }


    private fun fetchAllQuestions() {
        viewModel.fetchQuestionList().observe(this, questionListObserver)
    }

    private val questionListObserver = Observer<Operation<List<Question>>> {
        if (it is Operation.Success) {
            updateQuizList(it.data)
        }
    }

    private fun updateQuizList(questionList: List<Question>) {
        quizListAdapter.updateQuestionList(questionList)
    }

    private fun showNoQuizFound() {

    }

    private fun observeCurrentQuestionRemainingTime(){
        viewModel.getQuestionTimerLiveData().observe(this, questionRemainingTimeObserver)
    }

    private val questionRemainingTimeObserver = Observer<Long>{
        updateQuestionRemainingTimeUI(it)
    }

    private fun updateQuestionRemainingTimeUI(time : Long){
        viewBinding.timeCountView.text = getTimeDurationString(time)
        viewBinding.timeCountProgressView.progress = viewModel.getRemainingTimeProgress()
    }
}