package com.example.wmn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wmn.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    lateinit var binding : ActivityChatBinding
    lateinit var messageadapter: MessageAdapter

    var chatList = ArrayList<MyMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        val msg = intent.getStringExtra("message")
        messageadapter = MessageAdapter()

        binding.recyclerMessages.layoutManager= LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerMessages.adapter = messageadapter
        chatList.add(MyMessage(msg.toString(), 1))
        chatList.add(MyMessage("챗봇 응답", 0))
        messageadapter.setData(list = chatList)

        binding.chatSendButton.setOnClickListener {
            if(binding.chatMessage.text.toString() != null){
                chatList.add(MyMessage(binding.chatMessage.text.toString(), 1))
                chatList.add(MyMessage("챗봇 응답", 0))
                binding.chatMessage.text.clear()
                messageadapter.setData(list = chatList)
            }
        }
    }
}