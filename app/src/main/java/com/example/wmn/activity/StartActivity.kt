package com.example.wmn.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wmn.databinding.ActivityStartBinding
import com.example.wmn.firstrun.UsernameActivity

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
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
        }

    }

    fun init(){
        binding.Button.setOnClickListener {
            val intent = Intent(this@StartActivity, MainActivity::class.java)
            startActivity(intent)
        }
        binding.textView2.setOnClickListener {
            val intent = Intent(this@StartActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}