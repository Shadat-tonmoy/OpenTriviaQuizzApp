package com.assesment.opentriviaquizapp.network.api

import com.assesment.opentriviaquizapp.model.QuestionItem
import retrofit2.http.GET

interface QuestionAPI {

    @GET("/api/questions")
    suspend fun getQuestions(): List<QuestionItem>
}