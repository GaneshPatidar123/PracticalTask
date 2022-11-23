package com.example.practicalwisdomleaf.Repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitHelper {
    val baseUrl = "https://picsum.photo/"
    var gson = GsonBuilder()
        .setLenient()
        .create()
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}