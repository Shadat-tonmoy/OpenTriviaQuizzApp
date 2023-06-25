package com.assesment.opentriviaquizapp.network.apiHanlder

import com.assesment.opentriviaquizapp.model.Question
import com.assesment.opentriviaquizapp.network.api.QuestionAPI

class QuestionApiHandlerImpl(private val questionAPI: QuestionAPI) : QuestionApiHandler {

    override suspend fun fetchAllQuestions(): List<Question> {
        val allQuestions = questionAPI.getQuestions()
        val questionList = allQuestions
            .map {
                Question(it.id, it.question, it.correctAnswer, (it.incorrectAnswers as MutableList)
                    .apply { add(it.correctAnswer) }
                    .shuffled())
            }
        return questionList
    }
}