package com.example.wmn.firstRunActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wmn.databinding.ActivityAddfridgeBinding

class AddfridgeActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddfridgeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddfridgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.addFridgeBtn.setOnClickListener{
            // add fridge
        }
    }


}