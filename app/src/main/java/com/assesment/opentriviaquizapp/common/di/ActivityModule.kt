package com.assesment.opentriviaquizapp.common.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.assesment.opentriviaquizapp.ui.helpers.ActivityNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideActivityNavigator(@ActivityContext context: Context) : ActivityNavigator{
        return ActivityNavigator(context as AppCompatActivity)
    }

}