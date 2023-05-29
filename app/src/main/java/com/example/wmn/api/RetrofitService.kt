package com.example.wmn.api

import android.provider.ContactsContract.Data
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @POST("user/register")
    fun postUser(@Body params: HashMap<String, String>) : Call<UserInfo>

    @GET("user/initNang/{userNo}")
    fun getNang(@Path("userNo") number : Int) :Call<ResponseBody>

    @GET("dialog/open/{userNo}")
    fun getOpening(@Path("userNo") number : Int) :Call<ResponseBody>
}
