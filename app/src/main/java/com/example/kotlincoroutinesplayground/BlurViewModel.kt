package com.example.kotlincoroutinesplayground

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.experimental.launch
import java.io.File

class BlurViewModel(
    private val imageUploadService: ImageUploadService
) : ViewModel() {

    fun uploadImage(imageUri: Uri) {
        //update UI state, i.e: show progress, etc
        launch(BACKGROUND) {
            val imageFile = File(imageUri.path)
            val imageFilePart = createImagePart(imageFile)
            imageUploadService.uploadBlurredImage(imageFilePart).await()
        }
    }

}