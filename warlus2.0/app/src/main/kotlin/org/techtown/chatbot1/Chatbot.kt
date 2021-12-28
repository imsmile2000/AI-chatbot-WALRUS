package org.techtown.chatbot1

import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

import java.util.*

import kotlin.collections.ArrayList

import okhttp3.OkHttpClient
import org.techtown.chatbot1.R
import org.techtown.chatbot1.databinding.ActivityChatbotBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.Response
import java.lang.Exception

class Chatbot : AppCompatActivity() {
    private lateinit var mainActivity: ActivityChatbotBinding
    private lateinit var messageList: ArrayList<Message>
    private lateinit var adapter: MessageAdapter
    private lateinit var ButtonList: ArrayList<BotResponse.Buttons>
    private lateinit var buttonAdapter: MessageAdapter.ButtonAdapter
    private lateinit var editText: EditText
    private lateinit var sendBtn: FloatingActionButton
    private lateinit var button_view: RecyclerView

    private val ip = "3.36.35.54:5005" //10.0.2.2:5005
    private val url = "http://$ip/webhooks/rest/" // ⚠️MUST END WITH "/"

    private val USER = "M-" + UUID.randomUUID().toString()
    private val BOT_TXT = "0"
    private val BOT_IMG = "1"
    private val BOT_BUT = "2"
    private val BOT_SHIP_FEE = "3"
    private val BOT_FREIGHT_FEE = "4"
    private val BOT_OVERDUE_FEE = "5"
    private val BOT_CHART_INOUT = "6"
    private val BOT_CHART_FAC = "7"
    private val BOT_FIRST_TXT = "8"

    private var speechRecognizer: SpeechRecognizer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        messageList = ArrayList<Message>()
        mainActivity = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(mainActivity.root)

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            getWindow().setStatusBarColor(Color.TRANSPARENT) }*/


        adapter = MessageAdapter(this, messageList)
        adapter.setHasStableIds(true)
        mainActivity.messageList.adapter = adapter

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mainActivity.messageList.layoutManager = linearLayoutManager


        mainActivity.voiceButton.setOnClickListener {
            startSTT()
        }

        mainActivity.sendButton.setOnClickListener {
            val msg = mainActivity.messageBox.text.toString().trim()

            if (msg != "") {
                sendMessage(msg)
                mainActivity.messageBox.setText("")
            } else {
                Toast.makeText(this, "Please enter a message.", Toast.LENGTH_SHORT).show()
            }


            val recyclerView: RecyclerView = findViewById(R.id.message_list) /*사용자 요청으로 시선고정*/
            recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }

        sendMessage(message = "Hello", display = false)
    }

    fun sendMessage(message: String, alternative: String = "", display: Boolean = true) {
        val displayedMessage = if (alternative.isNullOrEmpty()) message else alternative
        var userMessage = Message(
            USER,
            message,
            -1
        ) // The message that will be sent to Rasa (payload in case of buttons)
        var userDisplayed = Message(
            USER,
            displayedMessage,
            -1
        ) // The message that will be displayed on screen (title in case of buttons)

        if (display) {
            messageList.add(userDisplayed)
            adapter.notifyDataSetChanged()
        }

        //val date = Date(System.currentTimeMillis())
        val okHttpClient = OkHttpClient()
        val retrofit = Retrofit.Builder().baseUrl(url).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val messageSender = retrofit.create(MessageSender::class.java)
        val response = messageSender.sendMessage(userMessage)

        response.enqueue(object : Callback<ArrayList<BotResponse>> {
            override fun onResponse(
                call: Call<ArrayList<BotResponse>>,
                response: Response<ArrayList<BotResponse>>
            ) {
                if (response.body() != null && response.body()!!.size != 0) {
                    for (i in 0 until response.body()!!.size) {
                        val message = response.body()!![i]

                        try {
                            if (message.text.isNotEmpty()) {
                                if (message.text.contains("&&&")) {
                                    System.out.println("check");
                                    messageList.add(
                                        Message(
                                            BOT_SHIP_FEE,
                                            message.text.replace("&&&", ""),
                                            i
                                        )
                                    )

                                } else if (message.text.contains("!!!")) {
                                    messageList.add(
                                        Message(
                                            BOT_FREIGHT_FEE,
                                            message.text.replace("!!!", ""),
                                            i
                                        )
                                    )
                                } else if (message.text.contains("***")) {
                                    messageList.add(
                                        Message(
                                            BOT_OVERDUE_FEE,
                                            message.text.replace("***", ""),
                                            i
                                        )
                                    )
                                } else {
                                    messageList.add(Message(BOT_TXT, message.text, i))
                                }
                            }


                        } catch (e: Exception) {
                        }

                        try {
                            if (message.buttons != null) {
                                messageList.add(Message(BOT_BUT,
                                    message.buttons as ArrayList<BotResponse.Buttons>,
                                    -1))
                            }
                        } catch (e: Exception) {
                        }

                        try {
                            if (message.custom != null) {
                                if (message.custom.contains("inout")) {
                                    messageList.add(Message(BOT_CHART_INOUT, message.custom, i))
                                } else if (message.custom.contains("permission")) {
                                    messageList.add(Message(BOT_CHART_FAC, message.custom, i))
                                }
                            }

                        } catch (e: Exception) {

                        }

                        adapter.notifyDataSetChanged()
                    }
                } else {
                    val message = "죄송해요, 서버로 부터의 응답이 없어요.:"
                    messageList.add(Message(BOT_TXT, message, 0))
                    adapter.notifyDataSetChanged()
                }

                val recyclerView: RecyclerView = findViewById(R.id.message_list) /*봇 응답으로 시선고정*/
                recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
            }

            override fun onFailure(call: Call<ArrayList<BotResponse>>, t: Throwable) {
                t.printStackTrace()
                val message = "죄송해요, 서버로 부터의 응답이 없어요.\n" + t.message
                messageList.add(Message(BOT_TXT, message, 0))
                adapter.notifyDataSetChanged()
            }
        })
    }


    inner class MessageAdapter(var context: Context, var messageList: ArrayList<Message>) :
        RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
        private var USER_LAYOUT = 1234
        private var BOT_TXT_LAYOUT = 0
        private var BOT_IMG_LAYOUT = 1
        private var BOT_BUT_LAYOUT = 2
        private var BOT_SHIP_FEE_LAYOUT = 3
        private var BOT_FREIGHT_FEE_LAYOUT = 4
        private var BOT_OVERDUE_FEE_LAYOUT = 5
        private var BOT_CHART_INOUT = 6
        private var BOT_CHART_FAC = 7
        private var BOT_FIRST_TXT_LAYOUT = 8
        private var BOT_FIRST_SHIP_FEE_LAYOUT = 9
        private var BOT_FIRST_FREIGHT_FEE_LAYOUT = 10
        private var BOT_FIRST_OVERDUE_FEE_LAYOUT = 11
        private var BOT_FIRST_CHART_INOUT = 12
        private var BOT_FIRST_CHART_FAC = 13


        inner class ButtonAdapter(
            var context: Context,
            var ButtonList: ArrayList<BotResponse.Buttons>
        ) :
            RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder>() {

            inner class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
                val button_view = view.findViewById<MaterialButton>(R.id.payload_button)
            }

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): ButtonAdapter.ButtonViewHolder {
                var view: View
                view =
                    LayoutInflater.from(context).inflate(R.layout.button_list_item, parent, false)
                return ButtonViewHolder(view)
            }

            override fun onBindViewHolder(holder: ButtonAdapter.ButtonViewHolder, position: Int) {
                val button = ButtonList[position]
                holder.button_view.text = button.title
                holder.button_view.setOnClickListener {
                    sendMessage(button.payload, button.title)

                }
            }

            override fun getItemCount(): Int {
                ButtonList.isEmpty() ?: return -1
                return ButtonList.size
            }

        }


        inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            val messageView = view.findViewById<TextView>(R.id.message_tv)
            val test_view = view.findViewById<RecyclerView>(R.id.button_recyclerview)
            val button_test = view.findViewById<Button>(R.id.button_tv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            var view: View

            if (viewType == BOT_TXT_LAYOUT || viewType == BOT_IMG_LAYOUT) {
                view =
                    LayoutInflater.from(context).inflate(R.layout.bot_message_box_2, parent, false)
            } else if (viewType == BOT_FIRST_TXT_LAYOUT) {
                view = LayoutInflater.from(context).inflate(R.layout.bot_message_box, parent, false)
            } else if (viewType == BOT_BUT_LAYOUT) {
                view =
                    LayoutInflater.from(context).inflate(R.layout.response_button_rv, parent, false)
            } else if (viewType == BOT_SHIP_FEE_LAYOUT) {

                view = LayoutInflater.from(context).inflate(R.layout.response_box_2, parent, false)
            } else if (viewType == BOT_FREIGHT_FEE_LAYOUT) {
                view = LayoutInflater.from(context).inflate(R.layout.response_box_2, parent, false)
            } else if (viewType == BOT_OVERDUE_FEE_LAYOUT) {
                view = LayoutInflater.from(context).inflate(R.layout.response_box_2, parent, false)
            } else if (viewType == BOT_CHART_INOUT) {
                view = LayoutInflater.from(context).inflate(R.layout.response_box_2, parent, false)
            } else if (viewType == BOT_CHART_FAC) {
                view = LayoutInflater.from(context).inflate(R.layout.response_box_2, parent, false)
            } else if (viewType == BOT_FIRST_CHART_INOUT) {
                view = LayoutInflater.from(context).inflate(R.layout.response_box_2, parent, false)
            } else if (viewType == BOT_FIRST_CHART_FAC) {
                view = LayoutInflater.from(context).inflate(R.layout.response_box_2, parent, false)
            } else if (viewType == BOT_FIRST_SHIP_FEE_LAYOUT) {
                System.out.println("ship");
                view = LayoutInflater.from(context).inflate(R.layout.response_box_2, parent, false)
            } else if (viewType == BOT_FIRST_FREIGHT_FEE_LAYOUT) {
                view = LayoutInflater.from(context).inflate(R.layout.response_box_2, parent, false)
            } else if (viewType == BOT_FIRST_OVERDUE_FEE_LAYOUT) {
                view = LayoutInflater.from(context).inflate(R.layout.response_box_2, parent, false)
            } else {
                view =
                    LayoutInflater.from(context).inflate(R.layout.user_message_box, parent, false)
            }

            return MessageViewHolder(view)
        }

        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
            val message = messageList[position]

            if (message.sender == BOT_TXT_LAYOUT.toString() && message.order == 0) {
                holder.messageView.text = message.message as String

                try {
                    //holder.image_view.visibility = View.GONE
                    //holder.button_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {
                }

            } else if (message.sender == BOT_SHIP_FEE_LAYOUT.toString() && message.order == 0) {
                holder.messageView.text = message.message as String
                holder.button_test.setOnClickListener {
                    var intent = Intent(holder.button_test?.context, ShippingFee::class.java)
                    ContextCompat.startActivity(holder.button_test.context, intent, null)
                }
                try {

                    //holder.messageView.visibility = View.GONE
                    //holder.image_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {

                }
            } else if (message.sender == BOT_FREIGHT_FEE_LAYOUT.toString() && message.order == 0) {
                holder.messageView.text = message.message as String
                holder.button_test.setOnClickListener {

                    var intent = Intent(holder.button_test?.context, FreightFee::class.java)
                    ContextCompat.startActivity(holder.button_test.context, intent, null)

                }
                try {

                    //holder.messageView.visibility = View.GONE
                    //holder.image_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {

                }
            } else if (message.sender == BOT_OVERDUE_FEE_LAYOUT.toString() && message.order == 0) {
                holder.messageView.text = message.message as String
                holder.button_test.setOnClickListener {
                    var intent = Intent(holder.button_test?.context, OverdueFee::class.java)
                    ContextCompat.startActivity(holder.button_test.context, intent, null)

                }
                try {

                    //holder.messageView.visibility = View.GONE
                    //holder.image_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {

                }
            } else if (message.sender == BOT_CHART_INOUT.toString() && message.order == 0) {
                holder.messageView.text = "선박 입출항 추가정보"
                holder.button_test.text = "차트"
                var data = message.message as String
                holder.button_test.setOnClickListener {
                    var intent = Intent(holder.button_test?.context, ChartInout::class.java)
                    intent.putExtra("inout", data)
                    ContextCompat.startActivity(holder.button_test.context, intent, null)

                }
                try {

                    //holder.messageView.visibility = View.GONE
                    //holder.image_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {

                }
            } else if (message.sender == BOT_CHART_FAC.toString() && message.order == 0) {
                holder.messageView.text = "시설사용허가 추가정보"
                holder.button_test.text = "차트"

                var data = message.message as String
                holder.button_test.setOnClickListener {
                    var intent = Intent(holder.button_test?.context, ChartFac::class.java)
                    intent.putExtra("fac", data)
                    ContextCompat.startActivity(holder.button_test.context, intent, null)

                }
                try {

                    //holder.messageView.visibility = View.GONE
                    //holder.image_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {

                }
            } else if (message.sender == BOT_TXT_LAYOUT.toString()) { // || message.sender == BOT_BUT_LAYOUT.toString()) {
                holder.messageView.text = message.message as String

                try {
                    //holder.image_view.visibility = View.GONE
                    //holder.button_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {
                }

            } else if (message.sender == BOT_BUT_LAYOUT.toString()) {

                ButtonList = ArrayList<BotResponse.Buttons>()

                buttonAdapter = ButtonAdapter(this@Chatbot, ButtonList)
                buttonAdapter.setHasStableIds(true)
                holder.test_view.adapter = buttonAdapter

                val layoutManager = LinearLayoutManager(this@Chatbot)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                holder.test_view.layoutManager = layoutManager


                var data = message.message as ArrayList<BotResponse.Buttons>
                for (i in 0 until data.size) {
                    ButtonList.add(data[i])
                }
                buttonAdapter.notifyDataSetChanged()

                try {
                    //holder.messageView.visibility = View.GONE
                    //holder.image_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {
                }

            } else if (message.sender == BOT_SHIP_FEE_LAYOUT.toString()) {
                holder.messageView.text = message.message as String
                holder.button_test.setOnClickListener {
                    var intent = Intent(holder.button_test?.context, ShippingFee::class.java)
                    ContextCompat.startActivity(holder.button_test.context, intent, null)
                }
                try {

                    //holder.messageView.visibility = View.GONE
                    //holder.image_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {

                }
            } else if (message.sender == BOT_FREIGHT_FEE_LAYOUT.toString()) {
                holder.messageView.text = message.message as String
                holder.button_test.setOnClickListener {

                    var intent = Intent(holder.button_test?.context, FreightFee::class.java)
                    ContextCompat.startActivity(holder.button_test.context, intent, null)

                }
                try {

                    //holder.messageView.visibility = View.GONE
                    //holder.image_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {

                }
            } else if (message.sender == BOT_OVERDUE_FEE_LAYOUT.toString()) {
                holder.messageView.text = message.message as String
                holder.button_test.setOnClickListener {
                    var intent = Intent(holder.button_test?.context, OverdueFee::class.java)
                    ContextCompat.startActivity(holder.button_test.context, intent, null)

                }
                try {

                    //holder.messageView.visibility = View.GONE
                    //holder.image_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {

                }
            } else if (message.sender == BOT_CHART_INOUT.toString()) {
                holder.messageView.text = "선박 입출항 추가정보"
                holder.button_test.text = "차트"
                var data = message.message as String
                holder.button_test.setOnClickListener {
                    var intent = Intent(holder.button_test?.context, ChartInout::class.java)
                    intent.putExtra("inout", data)
                    ContextCompat.startActivity(holder.button_test.context, intent, null)

                }
                try {

                    //holder.messageView.visibility = View.GONE
                    //holder.image_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {

                }
            } else if (message.sender == BOT_CHART_FAC.toString()) {
                holder.messageView.text = "시설사용허가 현황"
                holder.button_test.text = "차트"

                var data = message.message as String
                holder.button_test.setOnClickListener {
                    var intent = Intent(holder.button_test?.context, ChartFac::class.java)
                    intent.putExtra("fac", data)
                    ContextCompat.startActivity(holder.button_test.context, intent, null)

                }
                try {

                    //holder.messageView.visibility = View.GONE
                    //holder.image_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {

                }
            } else { // USER_LAYOUT
                holder.messageView.text = message.message as String

                try {
                    //holder.image_view.visibility = View.GONE
                    //holder.time_view.visibility = View.GONE
                } catch (e: Exception) {
                }
            }

        }

        override fun getItemCount(): Int {
            return messageList.size
        }

        override fun getItemViewType(position: Int): Int {
            super.getItemViewType(position)

            val view = messageList[position]

            if (view.sender == BOT_TXT_LAYOUT.toString() && view.order == 0) {
                return BOT_FIRST_TXT_LAYOUT
            } else if (view.sender == BOT_BUT_LAYOUT.toString()) {
                return BOT_BUT_LAYOUT
            } else if (view.sender == BOT_SHIP_FEE_LAYOUT.toString() && view.order == 0) {
                return BOT_FIRST_SHIP_FEE_LAYOUT
            } else if (view.sender == BOT_FREIGHT_FEE_LAYOUT.toString() && view.order == 0) {

                return BOT_FIRST_FREIGHT_FEE_LAYOUT
            } else if (view.sender == BOT_CHART_INOUT.toString() && view.order == 0) {
                return BOT_FIRST_CHART_INOUT
            } else if (view.sender == BOT_CHART_FAC.toString() && view.order == 0) {
                return BOT_FIRST_CHART_FAC
            } else if (view.sender == BOT_OVERDUE_FEE_LAYOUT.toString() && view.order == 0) {
                return BOT_FIRST_OVERDUE_FEE_LAYOUT
            } else if (view.sender == BOT_SHIP_FEE_LAYOUT.toString()) {
                return BOT_SHIP_FEE_LAYOUT
            } else if (view.sender == BOT_FREIGHT_FEE_LAYOUT.toString()) {
                return BOT_FREIGHT_FEE_LAYOUT
            } else if (view.sender == BOT_CHART_INOUT.toString()) {
                return BOT_CHART_INOUT
            } else if (view.sender == BOT_CHART_FAC.toString()) {
                return BOT_CHART_FAC
            } else if (view.sender == BOT_OVERDUE_FEE_LAYOUT.toString()) {
                return BOT_OVERDUE_FEE_LAYOUT
            } else if (view.sender == BOT_TXT_LAYOUT.toString()) {
                return BOT_TXT_LAYOUT
            } else {
                return USER_LAYOUT
            }
        }

    }

    private fun startSTT() {
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this).apply {
            setRecognitionListener(recognitionListener())
            startListening(speechRecognizerIntent)
        }

    }

    private fun recognitionListener() = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) =
            Toast.makeText(this@Chatbot, "음성을 인식해주세요", Toast.LENGTH_SHORT).show()

        override fun onRmsChanged(rmsdB: Float) {}

        override fun onBufferReceived(buffer: ByteArray?) {}

        override fun onPartialResults(partialResults: Bundle?) {}

        override fun onEvent(eventType: Int, params: Bundle?) {}

        override fun onBeginningOfSpeech() {}

        override fun onEndOfSpeech() {}

        override fun onError(error: Int) {
            when (error) {
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> Toast.makeText(this@Chatbot,
                    "마이크 권한을 추가해주세요",
                    Toast.LENGTH_SHORT).show()
            }
        }

        override fun onResults(results: Bundle) {
            val edt = findViewById<TextView>(R.id.message_box)
            edt.setText(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!![0])
        }
    }

    override fun onDestroy() {

        if (speechRecognizer != null) {
            speechRecognizer!!.stopListening()
        }

        super.onDestroy()
    }


}