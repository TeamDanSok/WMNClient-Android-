package com.example.wmn.firstRunActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.wmn.activity.StartActivity
import com.example.wmn.api.RetrofitBuilder
import com.example.wmn.api.UserInfo
import com.example.wmn.databinding.ActivityAddfridgeBinding
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

class AddfridgeActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddfridgeBinding

    lateinit var db: UsernameDatabase

    var list = ArrayList<Username>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddfridgeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        db = UsernameDatabase.getDatabase(this)
        binding.addFridgeBtn.setOnClickListener{
            // add fridge
            http()
        }
    }

    private fun http() {
        CoroutineScope(Dispatchers.Main).launch {
            val list = withContext(Dispatchers.IO) {
                db.usernameDao().getUser() as ArrayList<Username>
            }
            Log.d("test", list[0].uId.toString())
            RetrofitBuilder.api.getNang(list[0].uId).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    Log.d("test", "냉장고등록연결성공")
                    Log.d("test", response.code().toString())
                    val intent = Intent(this@AddfridgeActivity, StartActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("test", "연결실패")
                }

            })
        }

    }


}