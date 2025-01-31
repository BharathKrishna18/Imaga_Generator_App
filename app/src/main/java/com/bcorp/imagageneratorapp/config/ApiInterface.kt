package com.bcorp.imagageneratorapp.config

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAIService {
    @POST("models/stabilityai/stable-diffusion-3-medium-diffusers")
    suspend fun generateImage(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") apiKey: String,
        @Body requestBody: ImageRequest
    ): Response<ResponseBody>
}
