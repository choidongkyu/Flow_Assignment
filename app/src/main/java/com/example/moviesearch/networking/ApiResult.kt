package com.example.moviesearch.networking

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T?) : ApiResult<T>()
    data class Error(val errorCode: Int) : ApiResult<Nothing>()
}