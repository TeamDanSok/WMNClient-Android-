package com.example.wmn.firstrun

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wmn.R
import com.example.wmn.activity.MainActivity
import com.example.wmn.databinding.ActivityHealthBinding

class HealthActivity : AppCompatActivity() {
    lateinit var binding : ActivityHealthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val healthEdit : EditText = findViewById(R.id.userhealth_et)
        val healthNextBtn : Button = findViewById(R.id.userhealth_next_btn)

        var healthInfo: String = ""

        healthNextBtn.isEnabled = false

        //input이 있을때만 버튼 활성화
        healthEdit.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                healthInfo = healthEdit.text.toString()
                healthNextBtn.isEnabled = healthInfo.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        healthNextBtn.setOnClickListener {
            Toast.makeText(this, healthInfo, Toast.LENGTH_SHORT).show()
            System.out.println("health info is "+healthInfo)
            //Main 화면으로 전환
            val intent = Intent(this@HealthActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun init() {
        binding.userhealthNextBtn.setOnClickListener{
            System.out.println(binding.userhealthEt.text.toString())
        }
    }
}