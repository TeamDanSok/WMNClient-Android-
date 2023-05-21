package com.example.wmn

import android.R.attr.text
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wmn.databinding.ActivityChatBinding
import java.util.Locale


class ChatActivity : AppCompatActivity() {
    lateinit var binding : ActivityChatBinding
    lateinit var messageadapter: MessageAdapter

    var chatList = ArrayList<MyMessage>()

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTTS()
        init()
    }

    private  fun initTTS() {
        tts = TextToSpeech( this, OnInitListener {
                status ->
                if (status == TextToSpeech.SUCCESS) {
                    Log.d("TTS", "Initialized")
                    val result = tts!!.setLanguage(Locale.KOREA) // 언어 선택
                    if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                        Log.e("TTS", "This Language is not supported")
                    }
                } else {
                    Log.e("TTS", "Initialization Failed!")
                }
            }
        )
    }
    override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
            tts = null
        }
        super.onDestroy()
    }
    private fun init(){
        val msg = intent.getStringExtra("message")
        messageadapter = MessageAdapter()

        binding.recyclerMessages.layoutManager= LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )

        messageadapter.itemClickListener = object:MessageAdapter.OnItemClickListener {
            override fun OnChatBotClick(message: String) {
                    Log.d("TTS", "I;m here")
                    tts!!.speak(message, TextToSpeech.QUEUE_ADD, null, "i1")
            }
        }





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