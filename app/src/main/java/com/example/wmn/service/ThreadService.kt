package com.example.wmn.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.wmn.api.RetrofitBuilder
import com.example.wmn.roomDB.Username
import com.example.wmn.roomDB.UsernameDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThreadService : Service() {

    var thread:Thread?=null
    var num = 0

    lateinit var db: UsernameDatabase

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        db = UsernameDatabase.getDatabase(this)
        if (thread == null) {
            thread = object:Thread("OKThread") {
                override fun run() {
                    try {

                        while (true) {
                            if (getOK()) {
                                Log.d("test", "ok 받았다")
                            }
                            Thread.sleep(2000)
                        }
                    }catch (e: InterruptedException) {
                        Thread.currentThread().interrupt()
                    }
                    thread = null
                }
            }
            thread!!.start()
            num++
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (thread != null)
            thread!!.interrupt()
        thread = null
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun getOK() : Boolean {
        var ok = false
        CoroutineScope(Dispatchers.Main).launch {
            val list = withContext(Dispatchers.IO) {
                db.usernameDao().getUser() as ArrayList<Username>
            }
            Log.d("test", list[0].uId.toString())
            RetrofitBuilder.api.getOK(list[0].uId)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>, response: Response<ResponseBody>
                    ) {
                        val code = response.code()
                        Log.d("test", "냉장고 응답요청 연결성공")
                        Log.d("test", code.toString())
                        if (code == 200) {
                            ok = true
                            Log.d("test", response.body().toString())
                        }
                        if (code == 202) {
                            ok = true
                            Log.d("test", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("test", "연결실패")
                    }

                })
        }
        return ok
    }
}