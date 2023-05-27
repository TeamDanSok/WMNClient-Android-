package com.example.wmn.api

import android.provider.ContactsContract.Data
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @POST("user/register")
    fun postUser(@Body params: HashMap<String, String>) : Call<UserInfo>
}
