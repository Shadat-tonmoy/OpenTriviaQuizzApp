package com.assesment.opentriviaquizapp.ui.helpers

import android.app.Activity
import android.content.Intent
import com.assesment.opentriviaquizapp.ui.quiz.QuizActivity

class ActivityNavigator(private val activity: Activity) {

    fun openQuizListScreen() {
        val intent = Intent(activity, QuizActivity::class.java)
        activity.startActivity(intent)
    }
}