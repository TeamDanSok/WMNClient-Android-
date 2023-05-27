package com.example.wmn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
            binding.message.text.clear()
            //채팅화면으로 전환시키면 됨
            val intent = Intent(this@MainActivity, ChatActivity::class.java)
            intent.putExtra("message",binding.message.text.toString())
            startActivity(intent)
        }
        binding.moveScreen.setOnClickListener {
            val intent = Intent(this@MainActivity, ListActivity::class.java)
            startActivity(intent)
        }
    }
}