package com.example.wmn.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    var api: RetrofitService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://117.16.137.222:3000/apis/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(RetrofitService::class.java)
    }
}