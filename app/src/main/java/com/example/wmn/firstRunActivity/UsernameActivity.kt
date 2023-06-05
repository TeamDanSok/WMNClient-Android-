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
import com.example.wmn.databinding.ActivityUsernameBinding

class UsernameActivity : AppCompatActivity() {
    lateinit var binding : ActivityUsernameBinding
    lateinit var username :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsernameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            usernameNextBtn.isEnabled = false

            //input이 있을때만 버튼 활성화
            usernameEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    username = usernameEt.text.toString()
                    usernameNextBtn.isEnabled = username.isNotEmpty()
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            usernameNextBtn.setOnClickListener {
                if (username != null) {
                    Toast.makeText(this@UsernameActivity, username, Toast.LENGTH_SHORT).show()
                    //좋아하는 음식조사로 전환
                    val intent = Intent(this@UsernameActivity, PreferenceIntroActivity::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)
                }
            }
        }
    }
}