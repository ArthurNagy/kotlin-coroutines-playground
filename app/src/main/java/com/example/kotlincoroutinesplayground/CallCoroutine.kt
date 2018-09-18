package com.example.kotlincoroutinesplayground

import kotlinx.coroutines.experimental.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.experimental.suspendCoroutine

val BACKGROUND = Dispatchers.Default

class RequestException(message: String) : Exception(message)

suspend fun <T> Call<T>.await(): T = suspendCoroutine { continuation ->
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                continuation.resume(response.body()!!)
            } else {
                continuation.resumeWithException(RequestException(response.message()))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWithException(t)
        }
    })
}





