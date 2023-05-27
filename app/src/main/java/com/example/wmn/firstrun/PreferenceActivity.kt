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
import com.example.wmn.databinding.ActivityPreferenceBinding

class PreferenceActivity : AppCompatActivity() {
    lateinit var binding : ActivityPreferenceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodPreferEdit : EditText = findViewById(R.id.food_prefer_et)
        val preferNextBtn : Button = findViewById(R.id.prefer_next_btn)

        var preferedFood: String = ""

        preferNextBtn.isEnabled = false

        //input이 있을때만 버튼 활성화
        foodPreferEdit.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                preferedFood = foodPreferEdit.text.toString()
                preferNextBtn.isEnabled = preferedFood.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        preferNextBtn.setOnClickListener {
            Toast.makeText(this, preferedFood, Toast.LENGTH_SHORT).show()
            //건강조사(ex.알러지)로 전환
            val intent = Intent(this@PreferenceActivity, HealthActivity::class.java)
            startActivity(intent)

        }
    }

    fun init() {
        binding.preferNextBtn.setOnClickListener{
            System.out.println(binding.foodPreferEt.text.toString())
        }
    }
}