package com.assesment.opentriviaquizapp.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.assesment.opentriviaquizapp.R
import com.assesment.opentriviaquizapp.common.base.BaseActivity
import com.assesment.opentriviaquizapp.databinding.ActivityEndBinding
import com.assesment.opentriviaquizapp.databinding.ActivityHistoryBinding
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
    }

    private fun fetchQuizHistory() {

    }
}