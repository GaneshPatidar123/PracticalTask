package com.example.practicalwisdomleaf.Repository

import com.example.practicalwisdomleaf.services.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

        suspend fun getAllMovies() = retrofitService.getListData(2,20)

}