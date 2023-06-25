package com.assesment.opentriviaquizapp.ui.helpers

import android.app.Activity
import android.content.Intent
import com.assesment.opentriviaquizapp.common.constants.Constants
import com.assesment.opentriviaquizapp.ui.end.EndActivity
import com.assesment.opentriviaquizapp.ui.history.HistoryActivity
import com.assesment.opentriviaquizapp.ui.quiz.QuizActivity

class ActivityNavigator(private val activity: Activity) {

    fun openQuizListScreen() {
        val intent = Intent(activity, QuizActivity::class.java)
        activity.startActivity(intent)
    }

    fun openEndScreen(quizHistoryId : String) {
        val intent = Intent(activity, EndActivity::class.java)
            .apply {
                putExtra(Constants.QUIZ_HISTORY_ID, quizHistoryId)
            }
        activity.startActivity(intent)
    }

    fun openHistoryScreen() {
        val intent = Intent(activity, HistoryActivity::class.java)
        activity.startActivity(intent)
    }
}