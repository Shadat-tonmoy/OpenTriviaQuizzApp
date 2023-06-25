package com.assesment.opentriviaquizapp.network.apiHanlder

import com.assesment.opentriviaquizapp.model.Question

interface QuestionApiHandler {

    suspend fun fetchAllQuestions(): List<Question>
}