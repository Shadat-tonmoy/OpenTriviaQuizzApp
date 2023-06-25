package com.assesment.opentriviaquizapp.localData

import android.content.Context
import android.content.SharedPreferences
import com.assesment.opentriviaquizapp.model.QuizHistory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class CacheHelper(private val context: Context) {
    companion object {
        private const val TAG = "CacheHelper"
        const val SHARED_PREF_NAME = "open_trivia_app_pref"
        const val QUIZ_HISTORY = "quiz_history"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    private val gson by lazy { Gson() }

    private fun cacheQuizHistory(historyList: List<QuizHistory>) {
        val type = object : TypeToken<List<QuizHistory>>() {}.type
        val json = gson.toJson(historyList, type)
        preferences.edit().remove(QUIZ_HISTORY).apply()
        preferences.edit().putString(QUIZ_HISTORY, json).apply()
    }


    fun getQuizHistory(): List<QuizHistory> {
        val listType: Type = object : TypeToken<List<QuizHistory?>?>() {}.type
        val json = preferences.getString(QUIZ_HISTORY, "")
        return if (!json.isNullOrEmpty()) {
            val urls: List<QuizHistory> = gson.fromJson(json, listType)
            urls
        } else emptyList()
    }

    fun addQuizHistory(quizHistory: QuizHistory) {
        val existingList = getQuizHistory() as MutableList
        existingList.add(quizHistory)
        cacheQuizHistory(existingList)
    }

}