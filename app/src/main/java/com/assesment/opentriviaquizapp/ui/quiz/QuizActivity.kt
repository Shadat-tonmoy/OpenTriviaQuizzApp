package com.assesment.opentriviaquizapp.ui.quiz

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieDrawable.RepeatMode
import com.assesment.opentriviaquizapp.R
import com.assesment.opentriviaquizapp.common.Operation
import com.assesment.opentriviaquizapp.common.base.BaseActivity
import com.assesment.opentriviaquizapp.common.constants.Constants
import com.assesment.opentriviaquizapp.common.constants.Constants.NEXT_SCREEN_DELAY
import com.assesment.opentriviaquizapp.databinding.ActivityQuizBinding
import com.assesment.opentriviaquizapp.model.Question
import com.assesment.opentriviaquizapp.ui.helpers.ActivityNavigator
import com.assesment.opentriviaquizapp.ui.helpers.getTimeDurationString
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuizActivity : BaseActivity() {


    companion object {
        private const val TAG = "QuizActivity"
    }

    private val viewModel: QuizViewModel by viewModels()

    @Inject
    lateinit var activityNavigator: ActivityNavigator

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
        setupToolbar()
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
        } else if(it is Operation.Failure) {


        }
    }

    private fun setupToolbar() {
        with(viewBinding){
            toolbar.title = getString(R.string.quiz)
            toolbar.navigationIcon =
                ContextCompat.getDrawable(this@QuizActivity, R.drawable.ic_arrow_back_white_24)
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    private fun updateQuizList(questionList: List<Question>) {
        viewBinding.loadingView.visibility = View.GONE
        quizListAdapter.updateQuestionList(questionList)
    }

    private fun showNoQuizFound() {

    }

    private fun observeCurrentQuestionRemainingTime() {
        viewModel.getQuestionTimerLiveData().observe(this, questionRemainingTimeObserver)
    }

    private val questionRemainingTimeObserver = Observer<Long> {
        updateQuestionRemainingTimeUI(it)
        if (viewModel.isCurrentQuestionTimeFinished()) {
            val currentIndex = viewBinding.questionList.currentItem
            if (quizListAdapter.isQuestionAnsweredAt(currentIndex)) {
                handleQuestionSubmission()
            } else {
                viewBinding.submitButton.isEnabled = false
                viewModel.addAnswerFlagToStack(Constants.WRONG)
                showIncorrectAnswerView()
                initLoadNextQuestionTask()
            }


        }
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
            initLoadNextQuestionTask()
        } else {
            Snackbar.make(
                viewBinding.root,
                getString(R.string.invalid_answer_msg),
                Snackbar.LENGTH_SHORT
            ).show()
        }


    }

    private fun initLoadNextQuestionTask() {
        handler.removeCallbacks(loadNextQuestionTask)
        handler.postDelayed(loadNextQuestionTask, NEXT_SCREEN_DELAY)
    }

    private val loadNextQuestionTask = Runnable {
        loadNextQuestion()
    }

    private fun loadNextQuestion() {
        viewBinding.answerTypeView.visibility = View.GONE
        answerStackListAdapter.updateQuestionList(viewModel.getAnswerFlagList())
        val currentIndex = viewBinding.questionList.currentItem
        if (currentIndex == quizListAdapter.itemCount - 1) {
            openEndScreen()
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

    private fun openEndScreen() {
        val quizId = viewModel.cacheQuizHistoryAndGetId()
        activityNavigator.openEndScreen(quizId)
    }
}