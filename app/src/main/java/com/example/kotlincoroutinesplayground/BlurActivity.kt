package com.example.kotlincoroutinesplayground

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.kotlincoroutinesplayground.databinding.ActivityBlurBinding
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.util.concurrent.Executors
import kotlin.coroutines.experimental.suspendCoroutine

class BlurActivity : AppCompatActivity() {

    private lateinit var viewModel: BlurViewModel
    private lateinit var viewBinding: ActivityBlurBinding
    private val coroutineLifecycle = CoroutineLifecycleObserver()
    private val executor = Executors.newCachedThreadPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_blur)
        viewModel = ViewModelProviders.of(this).get(BlurViewModel::class.java)
        lifecycle.addObserver(coroutineLifecycle)


        launch(UI) {
            val deferredResult: Deferred<Unit> = async {
                // do stuff on the background thread
            }
            deferredResult.await()

            withContext(BACKGROUND) {

            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //...
        val imageUri = data!!.data!!

        val blurAsyncTask = object : AsyncTask<Uri, Int, Uri>() {
            override fun doInBackground(vararg params: Uri?): Uri {
                return blurImage(params)
            }

            override fun onPostExecute(result: Uri?) {
                viewBinding.image.setImageURI(result)
                viewModel.uploadImage(result)
            }

        }


        val disposable = Single
            .fromCallable { blurImage(imageUri) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resultImage ->
                viewBinding.image.setImageURI(resultImage)
                viewModel.uploadImage(resultImage)
            }, { error ->
            })

        disposable.dispose()

        blurAsyncTask.execute(imageUri)

        Executors.newCachedThreadPool().execute {
            val resultImage = blurImage(imageUri)
            runOnUiThread {
                viewBinding.image.setImageURI(resultImage)
                viewModel.uploadImage(resultImage)
            }
        }


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

//    suspend fun myFirstSuspendFunction(): ResultType = suspendCoroutine { continuation ->
//        // do some heavy work which suspends
//        // resume the coroutine continuation with a result:
//        continuation.resume(result)
//        // or if there was an error:
//        continuation.resumeWithException(exception)
//    }

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
