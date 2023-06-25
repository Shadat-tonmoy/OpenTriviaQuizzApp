package com.assesment.opentriviaquizapp.common.di

import android.content.Context
import com.assesment.opentriviaquizapp.localData.CacheHelper
import com.assesment.opentriviaquizapp.network.api.QuestionAPI
import com.assesment.opentriviaquizapp.network.apiHanlder.QuestionApiHandler
import com.assesment.opentriviaquizapp.network.apiHanlder.QuestionApiHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getAPIBaseURL())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideQuestionAPI(retrofit: Retrofit): QuestionAPI {
        return retrofit.create(QuestionAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideQuestionAPIHandler(questionAPI: QuestionAPI): QuestionApiHandler {
        return QuestionApiHandlerImpl(questionAPI)
    }

    private fun getAPIBaseURL(): String {
        return "https://the-trivia-api.com"
    }

    @Provides
    @Singleton
    fun provideCacheHelper(@ApplicationContext context: Context): CacheHelper {
        return CacheHelper(context)
    }

}