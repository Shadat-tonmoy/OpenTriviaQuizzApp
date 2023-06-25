package com.assesment.opentriviaquizapp.model

import com.assesment.opentriviaquizapp.ui.helpers.addLeadingZero
import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Timestamp

data class QuizHistory(
    @SerializedName("id") var id: String,
    @SerializedName("timeStamp") var timeStamp: Long,
    @SerializedName("totalCorrect") var totalCorrect: Int,
    @SerializedName("totalWrong") var totalWrong: Int,
    @SerializedName("totalQuestion") var totalQuestion: Int,
    @SerializedName("score") var score: Int
) {
    fun getScoreText(): String {
        return "${addLeadingZero(score)}/${addLeadingZero(totalQuestion)}"
    }

    fun getQuizTimeText() : String {
        val timestamp = Timestamp(timeStamp)
        val date = Date(timestamp.time)
        return date.toString()
    }
}
