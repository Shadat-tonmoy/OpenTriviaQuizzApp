package com.assesment.opentriviaquizapp.ui.quiz

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.assesment.opentriviaquizapp.R
import com.assesment.opentriviaquizapp.common.constants.Constants.CORRECT
import com.assesment.opentriviaquizapp.databinding.AnswerStackItemViewBinding
import com.assesment.opentriviaquizapp.databinding.SingleQuestionLayoutBinding
import com.assesment.opentriviaquizapp.model.Question

class AnswerStackListAdapter(private val context: Context) :
    RecyclerView.Adapter<AnswerStackListAdapter.ViewHolder>() {

    private var answerList: List<Int> = mutableListOf()

    companion object {
        private const val TAG = "AnswerStackListAdapter"
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            AnswerStackItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = answerList[position]
        holder.bindQuestion(question)
    }

    override fun getItemCount(): Int {
        return answerList.size
    }

    fun updateQuestionList(list: List<Int>) {
        answerList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val viewBinding: AnswerStackItemViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindQuestion(answer: Int) {
            with(viewBinding) {
                if (answer == CORRECT) {
                    answerIcon.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.check_green_24
                        )
                    )
                } else {
                    answerIcon.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.close_red_24
                        )
                    )
                }


            }

        }
    }
}