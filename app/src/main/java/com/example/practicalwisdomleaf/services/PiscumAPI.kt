package com.example.practicalwisdomleaf.services

import com.example.practicalwisdomleaf.model.PiscumData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PiscumAPI {
    @GET("v2/list")
    fun getListData( @Query("page") start: Int,
                   @Query("limit") limit: Int): Call<List<PiscumData>>
}