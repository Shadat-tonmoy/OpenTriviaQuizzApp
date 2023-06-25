package com.assesment.opentriviaquizapp.ui.history

import android.graphics.Path.Op
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.assesment.opentriviaquizapp.R
import com.assesment.opentriviaquizapp.common.Operation
import com.assesment.opentriviaquizapp.common.base.BaseActivity
import com.assesment.opentriviaquizapp.databinding.ActivityEndBinding
import com.assesment.opentriviaquizapp.databinding.ActivityHistoryBinding
import com.assesment.opentriviaquizapp.model.QuizHistory
import com.assesment.opentriviaquizapp.ui.end.EndViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryActivity : BaseActivity() {

    companion object {
        private const val TAG = "HistoryActivity"
    }

    private val viewModel: HistoryViewModel by viewModels()

    private val viewBinding: ActivityHistoryBinding by lazy {
        ActivityHistoryBinding.inflate(
            LayoutInflater.from(
                this
            )
        )
    }

    private val historyListAdapter: HistoryListAdapter by lazy {
        HistoryListAdapter(this, historyItemListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initUI()
        fetchQuizHistory()
    }

    private fun initUI() {
        setContentView(viewBinding.root)
        with(viewBinding) {
            historyList.adapter = historyListAdapter
        }
    }

    private fun fetchQuizHistory() {
        viewModel.getQuizHistory().observe(this, historyListObserver)
    }

    private val historyListObserver = Observer<Operation<List<QuizHistory>>> {
        if (it is Operation.Success) {
            historyListAdapter.updateHistoryList(it.data)
        }
    }

    private val historyItemListener = object : HistoryListAdapter.Listener {
        override fun onHistoryClicked(quizHistory: QuizHistory) {

        }

    }
}