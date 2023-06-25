package com.assesment.opentriviaquizapp.ui.history

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assesment.opentriviaquizapp.databinding.HistorySingleRowBinding
import com.assesment.opentriviaquizapp.model.QuizHistory
import com.assesment.opentriviaquizapp.ui.helpers.addLeadingZero

class HistoryListAdapter(private val context: Context, private val listener: Listener) :
    RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {

    private var historyList: List<QuizHistory> = mutableListOf()

    interface Listener {
        fun onHistoryClicked(quizHistory: QuizHistory)

    }

    companion object {
        private const val TAG = "HistoryListAdapter"
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            HistorySingleRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = historyList[position]
        holder.bindHistory(history, position + 1)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun updateHistoryList(list: List<QuizHistory>) {
        historyList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val viewBinding: HistorySingleRowBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindHistory(quizHistory: QuizHistory, index: Int) {
            with(viewBinding) {
                quizTitleView.text = "Quiz ${addLeadingZero(index)}"
                quizTimeView.text = quizHistory.getQuizTimeText()
                quizScoreView.text = quizHistory.getScoreText()
                root.setOnClickListener { listener.onHistoryClicked(quizHistory) }

            }

        }
    }
}