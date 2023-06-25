package com.assesment.opentriviaquizapp.common

sealed class Operation<out T>{
    data class Success<out T>(val data : T) : Operation<T>()
    data class Failure<out T>(val exception: Exception) : Operation<T>()
}