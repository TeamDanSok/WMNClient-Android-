package com.example.wmn.activity

import android.content.Intent
import android.icu.text.DateTimePatternGenerator.PatternInfo.OK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
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
import kotlin.math.log

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding

    lateinit var db: UsernameDatabase
    var runThread = false
    var thread : Thread? = null
    var ok = false
    var body = ""

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
//            if (thread == null) {
//                thread = object:Thread("OKThread") {
//                    override fun run() {
//                        try {
//
//                            while (true) {
//                                getOK()
//                                Thread.sleep(2000)
//                            }
//                        }catch (e: InterruptedException) {
//                            Thread.currentThread().interrupt()
//                        }
//                    }
//                }
//                thread!!.start()
//            }
//            val intent = Intent(this@StartActivity, ThreadService::class.java)
//            startService(intent)
        }


    }

    override fun onDestroy() {
        thread?.interrupt()

        super.onDestroy()
    }

    fun init(){
        db = UsernameDatabase.getDatabase(this)
        binding.Button.setOnClickListener {
            Log.d("test", ok.toString())
                getOpening()
                val intent = Intent(this@StartActivity, ChatActivity::class.java)
                intent.putExtra("data", body)
                startActivity(intent)
        }
        binding.textView2.setOnClickListener {
                getOpening()
                val intent = Intent(this@StartActivity, ChatActivity::class.java)
                intent.putExtra("data", body)
                startActivity(intent)
        }
    }
    private fun getOpening() : Boolean {
       var success = false
        CoroutineScope(Dispatchers.Main).launch {
            val list = withContext(Dispatchers.IO) {
                db.usernameDao().getUser() as ArrayList<Username>
            }
            //Log.d("test", list[0].uId.toString())
            RetrofitBuilder.api.getOpening(list[0].uId)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>, response: Response<ResponseBody>
                    ) {
                        Log.d("test", "냉장고 오프닝 연결성공")
                        Log.d("test", response.code().toString())
                        body = response.body().toString()
                        success = true

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("test", "연결실패")
                        success = false
                    }

                })
        }
        return success
    }
//    inner class ProgressThread:Thread() {
//        override fun run() {
//            while (runThread) {
//                if(binding.progressBar.progress == binding.progressBar.max) {
//                    runThread = false
//                    binding.progressBar.progress = 0


//    fun getOK() {
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
//                        if (code == 200 || code == 204) {
//                            ok = true
//                        } else {
//                            ok = false
//                        }
//                    }
//
//                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                        Log.d("test", "연결실패")
//                    }
//
//                })
//        }
//    }
}