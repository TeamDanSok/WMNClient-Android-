package com.example.wmn.firstRunActivity

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.provider.SyncStateContract.Helpers.insert
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wmn.R
import com.example.wmn.activity.StartActivity
import com.example.wmn.api.RetrofitBuilder
import com.example.wmn.api.RetrofitService
import com.example.wmn.api.UserInfo
import com.example.wmn.databinding.ActivityHealthBinding
import com.example.wmn.roomDB.Username
import com.example.wmn.roomDB.UsernameDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HealthActivity : AppCompatActivity() {
    lateinit var binding : ActivityHealthBinding
    lateinit var healthInfo : String
    lateinit var preferredFood : String
    lateinit var username : String
    lateinit var db: UsernameDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = getIntent()
        username = intent.getStringExtra("username").toString()
        preferredFood = intent.getStringExtra("preferredFood").toString()
        init()
    }

    private fun init() {
        db = UsernameDatabase.getDatabase(this)

        binding.apply {
            userhealthNextBtn.isEnabled = false

            //input이 있을때만 버튼 활성화
            userhealthEt.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    healthInfo = userhealthEt.text.toString()
                    userhealthNextBtn.isEnabled = healthInfo.isNotEmpty()
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            userhealthNextBtn.setOnClickListener {
                Toast.makeText(this@HealthActivity, healthInfo, Toast.LENGTH_SHORT).show()
                System.out.println("health info is "+healthInfo)
                //Main 화면으로 전환
                http()
                val intent = Intent(this@HealthActivity, StartActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun http() {
        var input = HashMap<String, String>()
        input.put("username", username)
        input.put("loveFood", preferredFood)
        input.put("danger", healthInfo)
        RetrofitBuilder.api.postUser(input).enqueue(object: Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                Log.d("test", "연결성공")
                var a = response.body()!!
                Log.d("test", a.id.toString())
                insertDB(a.id)
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("test", "연결실패")
            }

        })

    }

    private fun insertDB(id: Int) {
        val users = Username(id)
        CoroutineScope(Dispatchers.IO).launch {
            db.usernameDao().insertUser(users)
        }
    }
}