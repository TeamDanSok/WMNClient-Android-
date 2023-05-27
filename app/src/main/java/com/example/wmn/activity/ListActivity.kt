package com.example.wmn.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wmn.recyclerView.ListAdapter
import com.example.wmn.R
import com.example.wmn.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding
    lateinit var adapter: ListAdapter



    val foodList = arrayOf("비엔나 소세지", "불고기 600g", "하인즈 케찹", "수지스 닭가슴살",
            "양파", "당근", "오이")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.transition.vertical_enter, R.transition.none)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.transition.none, R.transition.vertical_exit)
    }
    private fun init() {
        binding.recyclerView.layoutManager= LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        adapter = ListAdapter(foodList)
        binding.recyclerView.adapter = adapter
    }
}