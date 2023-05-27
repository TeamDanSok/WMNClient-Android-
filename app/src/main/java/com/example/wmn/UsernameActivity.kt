package com.example.wmn

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wmn.databinding.ActivityUsernameBinding

class UsernameActivity : AppCompatActivity() {
    lateinit var binding : ActivityUsernameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsernameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameEdit : EditText = findViewById(R.id.username_et)
        val usernameNextBtn : Button = findViewById(R.id.username_next_btn)

        var username: String = ""

        usernameNextBtn.isEnabled = false

        //input이 있을때만 버튼 활성화
        usernameEdit.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                username = usernameEdit.text.toString()
                usernameNextBtn.isEnabled = username.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        usernameNextBtn.setOnClickListener {
            Toast.makeText(this, username, Toast.LENGTH_SHORT).show()
            //좋아하는 음식조사로 전환
            val intent = Intent(this@UsernameActivity, PreferenceActivity::class.java)
            startActivity(intent)
        }
    }

    fun init() {
        binding.usernameNextBtn.setOnClickListener{
            System.out.println(binding.usernameEt.text.toString())
        }
    }
}