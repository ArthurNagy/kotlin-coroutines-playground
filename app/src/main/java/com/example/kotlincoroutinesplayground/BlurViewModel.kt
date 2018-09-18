package com.example.kotlincoroutinesplayground

import android.net.Uri
import java.io.File

class BlurViewModel(
    private val restApiService: RestApiService
) : CoroutineViewModel() {

    fun uploadImage(imageUri: Uri) {
        //update UI state, i.e: show progress, etc
        launchWithParent(BACKGROUND) {
            val imageFile = File(imageUri.path)
            val imageFilePart = createImagePart(imageFile)
            val response = restApiService.uploadBlurredImage(imageFilePart).await()
            //update UI state, etc.
        }
    }

}