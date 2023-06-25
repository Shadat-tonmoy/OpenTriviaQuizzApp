package com.assesment.opentriviaquizapp.model

import com.google.gson.annotations.SerializedName

data class QuizHistory(
    @SerializedName("id") var id: String,
    @SerializedName("timeStamp") var timeStamp: Long,
    @SerializedName("totalCorrect") var totalCorrect: Int,
    @SerializedName("totalWrong") var totalWrong: Int,
    @SerializedName("totalQuestion") var totalQuestion: Int,
    @SerializedName("score") var score: Int
)
