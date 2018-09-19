package com.example.kotlincoroutinesplayground

import android.net.Uri
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.concurrent.Executors

class BlurViewModel(
    private val restApiService: RestApiService
) : CoroutineViewModel() {

//    fun uploadImage(imageUri: Uri) {
//        //update UI state, i.e: show progress, etc
//        launchWithParent(BACKGROUND) {
//            val imageFile = File(imageUri.path)
//            val imageFilePart = createImagePart(imageFile)
//            val response = restApiService.uploadBlurredImage(imageFilePart).await()
//            //update UI state, etc.
//        }
//    }

    private val disposables = CompositeDisposable()

    private val executors = Executors.newCachedThreadPool()

//    fun uploadImage(imageUri: Uri) {
//        executors.execute {
//            val imageFile = File(imageUri.path)
//            val imageFilePart = createImagePart(imageFile)
//            restApiService.uploadBlurredImage(imageFilePart).enqueue(object : Callback<RestApiService.BlurResponse> {
//                override fun onResponse(call: Call<RestApiService.BlurResponse>, response: Response<RestApiService.BlurResponse>) {
//
//                }
//
//                override fun onFailure(call: Call<RestApiService.BlurResponse>, t: Throwable) {
//                }
//
//            })
//        }
//    }

    fun uploadImage(imageUri: Uri) {
        disposables.add(
            Single.fromCallable {
                val imageFile = File(imageUri.path)
                createImagePart(imageFile)
            }.flatMap { imageFilePart ->
                restApiService.uploadBlurredImage(imageFilePart)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ blurResponse ->
                    //update UI state, etc.
                }, { error ->

                })
        )
    }

}