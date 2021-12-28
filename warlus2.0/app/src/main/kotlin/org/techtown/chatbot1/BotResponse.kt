package org.techtown.chatbot1

class BotResponse(var recipient: String = "1234", var text: String = "", var image: String = "", var buttons: List<Buttons>,var custom: String ="") {
    inner class Buttons(var payload: String, var title: String = "Button")
}