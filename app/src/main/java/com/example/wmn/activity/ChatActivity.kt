package com.example.wmn.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.example.wmn.api.RetrofitBuilder
import com.example.wmn.recyclerView.MessageAdapter
import com.example.wmn.chat.MyMessage
import com.example.wmn.databinding.ActivityChatBinding
import com.example.wmn.roomDB.Username
import com.example.wmn.roomDB.UsernameDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale
import java.util.Timer
import java.util.TimerTask


class ChatActivity : AppCompatActivity() {
    lateinit var binding : ActivityChatBinding
    lateinit var messageadapter: MessageAdapter
    lateinit var db: UsernameDatabase

    var chatList = ArrayList<MyMessage>()

    private var tts: TextToSpeech? = null
    private var speechRecognizer : SpeechRecognizer? = null
    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTTS()
        init()
    }

    private fun initTTS() {
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
        if (speechRecognizer != null) {
            speechRecognizer!!.destroy()
            speechRecognizer!!.cancel()
            speechRecognizer = null
        }
        super.onDestroy()
    }

    private fun startSTT(){
        CheckPermission()
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this).apply {
            setRecognitionListener(recognitionListener())
            startListening(speechRecognizerIntent)
        }
    }

    fun CheckPermission() {
        //인터넷이나 녹음 권한이 없으면 권한 요청
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_DENIED
            || ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(
                    android.Manifest.permission.INTERNET,
                    android.Manifest.permission.RECORD_AUDIO
                ), REQUEST_CODE
            )
        }
    }
    private fun recognitionListener() = object : RecognitionListener {

        override fun onReadyForSpeech(params: Bundle?) {
            Toast.makeText(this@ChatActivity, "음성인식 시작", Toast.LENGTH_SHORT).show()
        }

        override fun onRmsChanged(rmsdB: Float) {}

        override fun onBufferReceived(buffer: ByteArray?) {}

        override fun onPartialResults(partialResults: Bundle?) {}

        override fun onEvent(eventType: Int, params: Bundle?) {}

        override fun onBeginningOfSpeech() {}

        override fun onEndOfSpeech() {}

        override fun onError(error: Int) {
            val message: String = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "오디오 에러"
                SpeechRecognizer.ERROR_CLIENT -> "클라이언트 에러"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "퍼미션 없음"
                SpeechRecognizer.ERROR_NETWORK -> "네트워크 에러"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "네트워크 타임아웃"
                SpeechRecognizer.ERROR_NO_MATCH -> "찾을 수 없음"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RECOGNIZER가 바쁨"
                SpeechRecognizer.ERROR_SERVER -> "서버가 이상함"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "말하는 시간초과"
                else -> "알 수 없는 오류임"
            }
            Toast.makeText(applicationContext, "에러가 발생하였습니다. : $message", Toast.LENGTH_SHORT).show()
        }

        override fun onResults(results: Bundle) {
            Toast.makeText(this@ChatActivity, "음성인식 종료", Toast.LENGTH_SHORT).show()
            val msg:ArrayList<String> = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!!
            var message:String = ""
            for (i in 0 until msg.size) {
                message += msg[i]
            }
            chatList.add(MyMessage(message, 1))
            chatList.add(MyMessage("챗봇 응답", 0))
            messageadapter.setData(list = chatList)
        }
    }
//    fun stopSTT() {
//        if (speechRecognizer != null) {
//            speechRecognizer!!.destroy()
//            speechRecognizer!!.cancel()
//            speechRecognizer = null
//        }
//    }
    private fun init(){
        val msg = intent.getStringExtra("message")
        messageadapter = MessageAdapter()

        binding.recyclerMessages.layoutManager= LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )

        messageadapter.itemClickListener = object: MessageAdapter.OnItemClickListener {
            override fun OnChatBotClick(message: String) {
                    Log.d("TTS", "I;m here")
                    tts!!.speak(message, TextToSpeech.QUEUE_ADD, null, "i1")
            }
        }


        binding.recyclerMessages.adapter = messageadapter


//        chatList.add(MyMessage(msg.toString(), 1))
//        chatList.add(MyMessage("챗봇 응답", 0))
        messageadapter.setData(list = chatList)

        binding.chatSendButton.setOnClickListener {
            if(binding.chatMessage.text.toString().isNotEmpty()){
                chatList.add(MyMessage(binding.chatMessage.text.toString(), 1))
                chatList.add(MyMessage("챗봇 응답", 0))
                binding.chatMessage.text.clear()
                messageadapter.setData(list = chatList)
                binding.recyclerMessages.scrollToPosition(messageadapter.itemCount - 1)
            }
        }

        val thread = Thread(Runnable {
            val timer = Timer()

            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    // 실행할 함수 호출
                    if (getOK()){
                        Log.d("test", "add : messages")
                    }
                }
            }, 0, 2000)
        })
        thread.start();

        binding.mic.setOnClickListener {
            startSTT()
        }
    }

    fun getOK() : Boolean {
        var ok = false
        CoroutineScope(Dispatchers.Main).launch {
            val list = withContext(Dispatchers.IO) {
                db.usernameDao().getUser() as ArrayList<Username>
            }
            Log.d("test", list[0].uId.toString())
            RetrofitBuilder.api.getOK(list[0].uId)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>, response: Response<ResponseBody>
                    ) {
                        val code = response.code()
                        Log.d("test", "냉장고 응답요청 연결성공")
                        Log.d("test", code.toString())
                        if (code == 200) {
                            ok = true
                            Log.d("test", response.body().toString())
                            getDialog()
                            messageadapter.setData(list = chatList)
                            messageadapter.notifyDataSetChanged()
                            binding.recyclerMessages.scrollToPosition(messageadapter.itemCount - 1)

                        }
                        if (code == 202) {
                            Log.d("test", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("test", t.toString())

                    }

                })
        }
        return ok
    }

    fun getDialog() : Boolean {
        var responses = false;
        CoroutineScope(Dispatchers.Main).launch {
            val list = withContext(Dispatchers.IO) {
                db.usernameDao().getUser() as ArrayList<Username>
            }
            Log.d("test", list[0].uId.toString())
            RetrofitBuilder.api.getDialog(list[0].uId)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>, response: Response<ResponseBody>
                    ) {
                        val code = response.code()
                        Log.d("test", "냉장고 응답요청 연결성공")
                        Log.d("test", code.toString())
                        if (code == 200) {

                            var respBody = JSONArray(response.body()?.string() )
                            Log.d("test", respBody.toString())
                            for (i in 0..respBody.length()-1){
                                var temp = respBody[i]
                                Log.d("test2", temp.toString())
                                chatList.add(MyMessage(JSONObject(temp.toString()).getString("wimnchat"), 0))
                                tts!!.speak(JSONObject(temp.toString()).getString("wimnchat"), TextToSpeech.QUEUE_ADD, null, "i1")
                            }

                            Log.d("test", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("test", "연결실패")
                    }

                })
        }
        return responses
    }

}