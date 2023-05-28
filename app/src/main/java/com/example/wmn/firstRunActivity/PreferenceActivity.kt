package com.example.wmn.firstRunActivity

import android.content.Intent
import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wmn.R
import com.example.wmn.databinding.ActivityPreferenceBinding

class PreferenceActivity : AppCompatActivity() {
    lateinit var binding : ActivityPreferenceBinding
    lateinit var preferredFood : String
    lateinit var username : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        username = intent.getStringExtra("username").toString()

        init()
    }

    private fun init() {
        binding.apply {
            //버튼 리스너 추가해야함

            preferEt.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    preferredFood = preferEt.text.toString()
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            preferNextBtn.setOnClickListener {
                if (preferredFood != null) {
                    Toast.makeText(this@PreferenceActivity, preferredFood, Toast.LENGTH_SHORT).show()
                    //건강조사(ex.알러지)로 전환
                    val intent = Intent(this@PreferenceActivity, HealthActivity::class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("preferredFood", preferredFood)
                    startActivity(intent)
                }
            }
        }
    }
}