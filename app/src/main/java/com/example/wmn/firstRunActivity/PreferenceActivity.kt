package com.example.wmn.firstRunActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
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
            preferNextBtn.isEnabled = true

            preferEt.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (::preferredFood.isInitialized) {
                        preferredFood += preferEt.text.toString()
                    }
                    else {
                        preferredFood = preferEt.text.toString()
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            //선호 음식 리스너 추가


            //다음 버튼
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
