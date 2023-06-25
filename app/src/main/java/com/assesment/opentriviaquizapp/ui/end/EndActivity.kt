package com.assesment.opentriviaquizapp.ui.end

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.assesment.opentriviaquizapp.R
import com.assesment.opentriviaquizapp.common.Operation
import com.assesment.opentriviaquizapp.common.base.BaseActivity
import com.assesment.opentriviaquizapp.common.constants.Constants
import com.assesment.opentriviaquizapp.databinding.ActivityEndBinding
import com.assesment.opentriviaquizapp.model.QuizHistory
import com.assesment.opentriviaquizapp.ui.helpers.ActivityNavigator
import com.assesment.opentriviaquizapp.ui.helpers.addLeadingZero
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EndActivity : BaseActivity() {

    private val viewModel: EndViewModel by viewModels()

    private val viewBinding: ActivityEndBinding by lazy {
        ActivityEndBinding.inflate(
            LayoutInflater.from(
                this
            )
        )
    }

    @Inject
    lateinit var activityNavigator: ActivityNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initUI()
        fetchQuizDetails()
    }

    private fun initUI() {
        setContentView(viewBinding.root)
        setupToolbar()
        with(viewBinding) {
            showHistoryButton.setOnClickListener { activityNavigator.openHistoryScreen() }
            playAgainButton.setOnClickListener { }
        }
    }

    private fun fetchQuizDetails() {
        if (intent.hasExtra(Constants.QUIZ_HISTORY_ID)) {
            val id = intent.getStringExtra(Constants.QUIZ_HISTORY_ID) ?: ""
            viewModel.getEndScreenQuizDetails(id).observe(this, quizHistoryObserver)
        }
    }

    private val quizHistoryObserver = Observer<Operation<QuizHistory>> {
        if (it is Operation.Success) {
            setQuizInfo(it.data)
        }
    }

    private fun setQuizInfo(quizHistory: QuizHistory) {
        with(viewBinding) {
            quizEndScore.text =
                "${addLeadingZero(quizHistory.score)}/${addLeadingZero(quizHistory.totalQuestion)}"
            totalCorrectValue.text = "${addLeadingZero(quizHistory.totalCorrect)}"
            totalWrongValue.text = "${addLeadingZero(quizHistory.totalWrong)}"

        }
    }

    private fun setupToolbar() {
        with(viewBinding) {
            toolbar.title = getString(R.string.quiz_finished)
            toolbar.navigationIcon =
                ContextCompat.getDrawable(this@EndActivity, R.drawable.ic_arrow_back_white_24)
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }
}