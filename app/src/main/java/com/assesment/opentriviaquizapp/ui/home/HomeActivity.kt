package com.assesment.opentriviaquizapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.assesment.opentriviaquizapp.R
import com.assesment.opentriviaquizapp.common.base.BaseActivity
import com.assesment.opentriviaquizapp.databinding.ActivityHomeBinding
import com.assesment.opentriviaquizapp.ui.helpers.ActivityNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewBinding by lazy { ActivityHomeBinding.inflate(LayoutInflater.from(this)) }

    @Inject
    lateinit var activityNavigator: ActivityNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initUI()
    }

    private fun initUI() {
        setContentView(viewBinding.root)
        with(viewBinding) {
            openQuizCard.setOnClickListener { activityNavigator.openQuizListScreen() }
            showHistoryCard.setOnClickListener { activityNavigator.openHistoryScreen() }
        }
    }
}