package com.example.kotlincoroutinesplayground

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.kotlincoroutinesplayground.databinding.ActivityBlurBinding
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import kotlin.coroutines.experimental.suspendCoroutine

class BlurActivity : AppCompatActivity() {

    private lateinit var viewModel: BlurViewModel
    private lateinit var viewBinding: ActivityBlurBinding
    private val coroutineLifecycle = CoroutineLifecycleObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_blur)
        viewModel = ViewModelProviders.of(this).get(BlurViewModel::class.java)
        lifecycle.addObserver(coroutineLifecycle)

        async { }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //...
        val imageUri = data!!.data!!
        launch(context = UI, parent = coroutineLifecycle.job) {

            //            val blurImageDeferred = async(BACKGROUND) {
//                blurImage(imageUri)
//            }
//
//            val blurredImageResult = blurImageDeferred.await()

            val resultImage = withContext(BACKGROUND) {
                blurImage(imageUri)
            }

            viewBinding.image.setImageURI(resultImage)

            viewModel.uploadImage(resultImage)
        }
    }

    suspend fun blurImage(imageUri: Uri): Uri = suspendCoroutine { continuation ->
        try {
            val imageBitmap = getBitmapFromImageUri(imageUri)
            val blurredImage = applyBlurOnBitmap(imageBitmap)
            val blurredImageFile = saveBitmapToTemporaryFile(blurredImage)
            continuation.resume(blurredImageFile)
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }


    private fun saveBitmapToTemporaryFile(blurredImage: Bitmap): Uri {
        TODO()
    }

    private fun applyBlurOnBitmap(imageBitmap: Bitmap): Bitmap {
        TODO()
    }

    fun getBitmapFromImageUri(imageUri: Uri): Bitmap {
        TODO()
    }


}
