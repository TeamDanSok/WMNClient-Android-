package com.example.wmn.activity

import android.content.Intent
import android.icu.text.DateTimePatternGenerator.PatternInfo.OK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wmn.api.RetrofitBuilder
import com.example.wmn.databinding.ActivityStartBinding
import com.example.wmn.firstRunActivity.UsernameActivity
import com.example.wmn.roomDB.Username
import com.example.wmn.roomDB.UsernameDatabase
import com.example.wmn.service.ThreadService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding

    lateinit var db: UsernameDatabase

    //var thread:Thread ?= null
    override fun onCreate(savedInstanceState: Bundle?) {

        val pref = getSharedPreferences("firstcheck", MODE_PRIVATE)
        val isFirstRun = pref.getBoolean("isFirstRun", true)
        System.out.println("1번 check");
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isFirstRun) {
            pref.edit().putBoolean("isFirstRun", false).apply()
            //최초실행시 작업
            //유저명 받기
            val usernameIntent = Intent(this@StartActivity, UsernameActivity::class.java)
            startActivity(usernameIntent)

        }
        else{
            //두번째 실행
            init()
            val intent = Intent(this@StartActivity, ThreadService::class.java)
            startService(intent)
        }


    }

    fun init(){
        db = UsernameDatabase.getDatabase(this)
        binding.Button.setOnClickListener {
            getOpening()
            val intent = Intent(this@StartActivity, ChatActivity::class.java)
            startActivity(intent)
        }
        binding.textView2.setOnClickListener {
            getOpening()
            val intent = Intent(this@StartActivity, ChatActivity::class.java)
            startActivity(intent)
        }
    }
    private fun getOpening() {
        CoroutineScope(Dispatchers.Main).launch {
            val list = withContext(Dispatchers.IO) {
                db.usernameDao().getUser() as ArrayList<Username>
            }
            Log.d("test", list[0].uId.toString())
            RetrofitBuilder.api.getOpening(list[0].uId).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    Log.d("test", "냉장고 오프닝 연결성공")
                    Log.d("test", response.code().toString())
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("test", "연결실패")
                }

            })
        }
    }

//    fun getOK() : Boolean {
//        var ok = false
//        CoroutineScope(Dispatchers.Main).launch {
//            val list = withContext(Dispatchers.IO) {
//                db.usernameDao().getUser() as ArrayList<Username>
//            }
//            Log.d("test", list[0].uId.toString())
//            RetrofitBuilder.api.getOK(list[0].uId)
//                .enqueue(object : Callback<ResponseBody> {
//                    override fun onResponse(
//                        call: Call<ResponseBody>, response: Response<ResponseBody>
//                    ) {
//                        val code = response.code()
//                        Log.d("test", "냉장고 응답요청 연결성공")
//                        Log.d("test", code.toString())
//                        if (code == 200) {
//                            ok = true
//                            Log.d("test", response.body().toString())
//                        }
//                        if (code == 202) {
//                            ok = true
//                            Log.d("test", response.body().toString())
//                        }
//                    }
//
//                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                        Log.d("test", "연결실패")
//                    }
//
//                })
//        }
//        return ok
//    }
}