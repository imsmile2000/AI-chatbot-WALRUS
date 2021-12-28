package org.techtown.chatbot1

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MessageSender {
    @POST("webhook")
    fun sendMessage(@Body userMessage: Message): Call<ArrayList<BotResponse>>
}