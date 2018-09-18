package com.example.kotlincoroutinesplayground

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

fun createImagePart(imageFile: File): MultipartBody.Part =
    MultipartBody.Part.createFormData("blurred_image", imageFile.name, RequestBody.create(MediaType.parse(".jpg"), imageFile))

interface ImageUploadService {

    @Multipart
    @POST("/blurred_image")
    fun uploadBlurredImage(@Part file: MultipartBody.Part): Call<BlurResponse>

    class BlurResponse

}