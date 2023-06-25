package com.assesment.opentriviaquizapp.ui.quiz

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assesment.opentriviaquizapp.databinding.SingleQuestionLayoutBinding
import com.assesment.opentriviaquizapp.model.Question

class QuizListAdapter(private val context: Context) : RecyclerView.Adapter<QuizListAdapter.ViewHolder>() {

    private var questionList : List<Question> = mutableListOf()

    interface Listener {
        fun onAnswerOptionSelected()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = SingleQuestionLayoutBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questionList[position]
        holder.bindQuestion(question)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    fun updateQuestionList(list : List<Question>) {
        questionList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val viewBinding : SingleQuestionLayoutBinding) : RecyclerView.ViewHolder(viewBinding.root){

        fun bindQuestion(question: Question) {
            with(viewBinding){
                questionText.text = question.question
                option1.text = question.options[0]
                option2.text = question.options[1]
                option3.text = question.options[2]
                option4.text = question.options[3]
                option1.setOnClickListener { question.selectUserAnswer(0) }
                option2.setOnClickListener { question.selectUserAnswer(1) }
                option3.setOnClickListener { question.selectUserAnswer(2) }
                option4.setOnClickListener { question.selectUserAnswer(3) }
            }

        }
    }
}