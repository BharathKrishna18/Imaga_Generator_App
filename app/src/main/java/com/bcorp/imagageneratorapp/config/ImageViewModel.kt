package com.bcorp.imagageneratorapp.config


import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ImageViewModel(config: Configuration) : ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    private val imageBitmap = MutableLiveData<Bitmap?>()

    val imageBit: LiveData<Bitmap?> = imageBitmap
    val errorMessage: LiveData<String> = _errorMessage

    private val scrw = config.screenWidthDp
    private val scrh = config.screenHeightDp

    fun generateImage(prompt: String, callback: () -> Unit) {
        viewModelScope.launch {
            try {
                val imageReq = ImageRequest(prompt = prompt)

                Log.d("ViewModel", "Entered ViewModel!")
                val call = RetrofitInstance.openApi.generateImage(requestBody = imageReq)

                if (call.isSuccessful) {
                    Log.d("Response", "Successful!")
                    val imageStream = call.body()?.byteStream()

                    imageStream?.let {
                        Log.d("Response", "Received Image!")
                        val bitmap = BitmapFactory.decodeStream(it)

                        val resized = bitmap?.let {
                            resizeBitmap(it, scrw, scrh)
                        }
                        Log.d("Response", "Resized Image!")
                        imageBitmap.postValue(resized)
                    }
                } else {
                    Log.d("Response", "Unsuccessful!")
                    _errorMessage.value = call.errorBody()?.string()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown Error"
            } finally {
                callback()
            }
        }
    }

    private fun resizeBitmap(original: Bitmap, screenwidth: Int, screenheight: Int): Bitmap {
        Log.d("Resize", "Entered resize!")
        val width = original.width
        val height = original.height
        val aspectRatio = width.toFloat() / height.toFloat()

        val newWidth: Int
        val newHeight: Int

        if (width > height) {
            newWidth = screenwidth
            newHeight = (screenwidth / aspectRatio).toInt()
        } else {
            newHeight = screenheight
            newWidth = (screenheight * aspectRatio).toInt()
        }

        return Bitmap.createScaledBitmap(original, newWidth, newHeight, true)
    }
}
