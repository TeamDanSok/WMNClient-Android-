package com.example.wmn.firstRunActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wmn.R
import com.example.wmn.activity.StartActivity
import com.example.wmn.databinding.ActivityHealthBinding

class HealthActivity : AppCompatActivity() {
    lateinit var binding : ActivityHealthBinding
    lateinit var healthInfo : String
    lateinit var preferredFood : String
    lateinit var username : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
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
                val intent = Intent(this@HealthActivity, StartActivity::class.java)
                startActivity(intent)
            }
        }
    }
}