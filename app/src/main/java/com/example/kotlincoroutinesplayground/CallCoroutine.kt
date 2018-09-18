package com.example.kotlincoroutinesplayground

import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val BACKGROUND = Dispatchers.Default

class RequestException(message: String) : Exception(message)

suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine { continuation ->
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





