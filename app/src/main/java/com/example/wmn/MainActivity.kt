package com.example.wmn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.wmn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        binding.sendButton.setOnClickListener {
            var message = binding.message.text.toString()
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            binding.message.text.clear()
            //채팅화면으로 전환시키면 됨
        }
    }
}