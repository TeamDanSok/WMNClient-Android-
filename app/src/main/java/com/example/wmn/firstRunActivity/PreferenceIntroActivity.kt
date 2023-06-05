package com.example.wmn.firstRunActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.wmn.databinding.ActivityPreferenceIntroBinding


class PreferenceIntroActivity : AppCompatActivity() {
    lateinit var binding : ActivityPreferenceIntroBinding
    lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferenceIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        username = intent.getStringExtra("username").toString()

        delayedMove(2500)
    }

    private fun delayedMove(delayMillis: Int){
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@PreferenceIntroActivity, PreferenceActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }, delayMillis.toLong())
    }
}