package com.assesment.opentriviaquizapp.ui.quiz

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieDrawable.RepeatMode
import com.assesment.opentriviaquizapp.R
import com.assesment.opentriviaquizapp.common.Operation
import com.assesment.opentriviaquizapp.common.base.BaseActivity
import com.assesment.opentriviaquizapp.common.constants.Constants
import com.assesment.opentriviaquizapp.common.constants.Constants.NEXT_SCREEN_DELAY
import com.assesment.opentriviaquizapp.databinding.ActivityQuizBinding
import com.assesment.opentriviaquizapp.model.Question
import com.assesment.opentriviaquizapp.ui.helpers.getTimeDurationString
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : BaseActivity() {


    companion object {
        private const val TAG = "QuizActivity"
    }

    private val viewModel: QuizViewModel by viewModels()

    private val viewBinding by lazy { ActivityQuizBinding.inflate(LayoutInflater.from(this)) }
    private val quizListAdapter by lazy { QuizListAdapter(this) }
    private val answerStackListAdapter by lazy { AnswerStackListAdapter(this) }
    private val handler: Handler = Handler(Looper.getMainLooper())

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
                updateCurrentQuestionLabel()
                startQuestionTimer()
            }
            answerStackView.adapter = answerStackListAdapter
            submitButton.setOnClickListener { handleQuestionSubmission() }
        }
        observeCurrentQuestionRemainingTime()

    }

    private fun updateCurrentQuestionLabel() {

        with(viewBinding) {
            val currentIndex = questionList.currentItem + 1
            questionCountLabel.text = getString(
                R.string.question_count_placeholder,
                "${currentIndex}",
                "${quizListAdapter.itemCount}"
            )
            currentScoreLabel.text =
                getString(R.string.current_score_placeholder, "${viewModel.currentScore}")
        }

    }

    private fun startQuestionTimer() {
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

    private fun observeCurrentQuestionRemainingTime() {
        viewModel.getQuestionTimerLiveData().observe(this, questionRemainingTimeObserver)
    }

    private val questionRemainingTimeObserver = Observer<Long> {
        updateQuestionRemainingTimeUI(it)
    }

    private fun updateQuestionRemainingTimeUI(time: Long) {
        viewBinding.timeCountView.text = getTimeDurationString(time)
        viewBinding.timeCountProgressView.progress = viewModel.getRemainingTimeProgress()
    }

    private fun handleQuestionSubmission() {
        val currentIndex = viewBinding.questionList.currentItem
        if (quizListAdapter.isQuestionAnsweredAt(currentIndex)) {
            viewBinding.submitButton.isEnabled = false
            if (quizListAdapter.isCorrectAnsweredAt(currentIndex)) {
                viewModel.addAnswerFlagToStack(Constants.CORRECT)
                showCorrectAnswerView()
            } else {
                viewModel.addAnswerFlagToStack(Constants.WRONG)
                showIncorrectAnswerView()
            }
            handler.removeCallbacks(loadNextQuestionTask)
            handler.postDelayed(loadNextQuestionTask, NEXT_SCREEN_DELAY)
        } else {
            Snackbar.make(
                viewBinding.root,
                getString(R.string.invalid_answer_msg),
                Snackbar.LENGTH_SHORT
            ).show()
        }


    }

    private val loadNextQuestionTask = Runnable {
        loadNextQuestion()
    }

    private fun loadNextQuestion() {
        viewBinding.answerTypeView.visibility = View.GONE
        answerStackListAdapter.updateQuestionList(viewModel.getAnswerFlagList())
        val currentIndex = viewBinding.questionList.currentItem
        if (currentIndex == quizListAdapter.itemCount - 1) {
            // show end screen
        } else {
            viewBinding.questionList.setCurrentItem(currentIndex + 1, true)
            viewBinding.submitButton.isEnabled = true
        }
    }

    private fun showCorrectAnswerView() {
        with(viewBinding) {
            answerTypeView.visibility = View.VISIBLE
            answerTypeView.setAnimation(R.raw.correct_answer)
            answerTypeView.playAnimation()
            answerTypeView.repeatCount = 5
        }

    }

    private fun showIncorrectAnswerView() {
        with(viewBinding) {
            answerTypeView.visibility = View.VISIBLE
            answerTypeView.setAnimation(R.raw.wrong_answer)
            answerTypeView.playAnimation()
            answerTypeView.repeatCount = 5
        }

    }
}