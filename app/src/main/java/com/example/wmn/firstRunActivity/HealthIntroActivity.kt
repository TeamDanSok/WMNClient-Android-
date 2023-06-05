package com.example.wmn.firstRunActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wmn.activity.StartActivity
import com.example.wmn.databinding.ActivityHealthIntroBinding

class HealthIntroActivity : AppCompatActivity() {
    lateinit var binding : ActivityHealthIntroBinding
    var isAllergic : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.apply {
            healthYesBtn.setOnClickListener{
                isAllergic = true
                val intent = Intent(this@HealthIntroActivity, HealthActivity::class.java)
                startActivity(intent)
            }

            healthNoBtn.setOnClickListener{
                isAllergic = false

                val intent = Intent(this@HealthIntroActivity, StartActivity::class.java)
                startActivity(intent)

            }
        }
    }
}