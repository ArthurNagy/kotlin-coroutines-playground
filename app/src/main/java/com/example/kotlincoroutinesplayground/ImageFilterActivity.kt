package com.example.kotlincoroutinesplayground

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.io.File
import java.io.FileOutputStream

class ImageFilterActivity : AppCompatActivity() {

    private lateinit var viewModel: ImageFilterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_filter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE || requestCode == GALLERY_REQUEST_CODE) {
                val imageUri = data!!.data

                launch(UI) {
                    val resultImage = withContext(Dispatchers.Default) {
                        applyFilterToImage(imageUri, selectedFilter)
                    }
                    viewModel.uploadImage(resultImage)
                }
            }
        }
    }

    suspend fun applyFilterToImage(imageUri: Uri, filter: Filter): File {
        //do some really heavy work
        val imageFile = File(cacheDir, imageUri.lastPathSegment)
        contentResolver.openInputStream(imageUri).use { imageInputStream ->
            FileOutputStream(imageFile).use { imageFileStream ->
                imageInputStream?.copyTo(imageFileStream)
            }
        }
        return imageFile
    }

    suspend private fun Bitmap.applyFilter(filter: Filter): Bitmap {
        // apply filter
        // ...
        return this
    }

    companion object {
        val CAMERA_REQUEST_CODE = 1
        val GALLERY_REQUEST_CODE = 2
        private val selectedFilter = Filter()
    }

    class Filter()


}
