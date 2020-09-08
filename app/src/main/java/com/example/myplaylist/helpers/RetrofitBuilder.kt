package com.example.myplaylist.helpers

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
/**
 * Created by Kailash Suthar on 04/9/2020.
 */
object RetrofitBuilder {

    private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

    /**
     * Initialize retrofit instance
     */
    private fun getRetrofit(): Retrofit {
        val client = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val apiService: ApiInterface = getRetrofit().create(ApiInterface::class.java)
}