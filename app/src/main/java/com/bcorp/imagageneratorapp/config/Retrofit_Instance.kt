package com.bcorp.imagageneratorapp.config

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {
   val openApi : OpenAIService by lazy {
       val client = OkHttpClient.Builder()
           .connectTimeout(60,TimeUnit.SECONDS)
           .readTimeout(60,TimeUnit.SECONDS)
           .writeTimeout(60,TimeUnit.SECONDS)
           .build()

       val retrofit = Retrofit.Builder()
           .baseUrl("https://api-inference.huggingface.co/")
           .addConverterFactory(GsonConverterFactory.create())
           .client(client)
           .build()

       retrofit.create(OpenAIService::class.java)
   }
}
