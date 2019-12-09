package com.example.androidimageupload

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.IOException

object HttpUtil {
    private val client = OkHttpClient()

    fun httpGet(url: String): String? {
        val request = Request.Builder()
            .url(url)
            .build()

        val response: Response
        try {
            response = client.newCall(request).execute()
        } catch (e: Exception) {
            throw IOException()
        }
        return response.body?.string()
    }


    // https://qiita.com/noriseto/items/f938abadd932af8b09d8
    fun uploadImage(url: String) {
        val file = File("file:///android_asset/hashimoto.png")

//        val requestBody = encodedImage.toRequestBody("plain/text".toMediaTypeOrNull())

        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "upload_file",
                "hashimoto.png",
                RequestBody.create("text/plain; charset=utf-8".toMediaTypeOrNull(), file)
            )
            .build()
        val request = Request
            .Builder()
            .url(url)
            .post(body)
            .build()


        val response: Response
        try {
            response = client.newCall(request).execute()
            println("httpconnection success ${response.body?.string()}")
        } catch (e: Exception) {
            println("httpconnection error${e.message} ${e.cause}")
            throw IOException()
        }
    }
}