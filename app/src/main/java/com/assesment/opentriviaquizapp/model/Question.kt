package com.assesment.opentriviaquizapp.model

import com.google.gson.annotations.SerializedName


data class QuestionItem(
    @SerializedName("category") val category: String,
    @SerializedName("id") val id: String,
    @SerializedName("correctAnswer") val correctAnswer: String,
    @SerializedName("incorrectAnswers") val incorrectAnswers: List<String>,
    @SerializedName("question") val question: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("type") val type: Type,
    @SerializedName("difficulty") val difficulty: Difficulty,
    @SerializedName("regions") val regions: List<Any?>,
    @SerializedName("isNiche") val isNiche: Boolean
)

enum class Difficulty {
    Easy,
    Hard,
    Medium
}

enum class Type {
    MultipleChoice
}

data class Question(
    @SerializedName("id") val id: String,
    @SerializedName("question") val question: String,
    @SerializedName("correctAnswer") val correctAnswer: String,
    @SerializedName("options") val options: List<String>,
    @SerializedName("userAnswer") var userAnswer: String = ""
) {
    fun isCorrect() : Boolean {
        return userAnswer == correctAnswer
    }

    fun selectUserAnswer(optionIndex : Int) {
        userAnswer = options[optionIndex]
    }
}
