package net.volachat.ui.feature.panel

import androidx.compose.runtime.mutableStateListOf
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.volachat.App
import net.volachat.util.ViewModel
import javax.inject.Inject

class PanelViewModel @Inject constructor() : ViewModel() {

    private val _isTryingToLogOut = MutableStateFlow(false)
    val isTryingToLogOut: StateFlow<Boolean> = _isTryingToLogOut

    val queueSendMessage = Channel<String>()
    val messages = mutableStateListOf<Message>()

    data class Message(val time: String,
                       val sender : String,
                       val message : String)

    init {
        connectChat()
    }

    suspend fun DefaultClientWebSocketSession.sendMessage(){
        while (true) {
            for (data in queueSendMessage) {
                try {
                    send(data)
                } catch (e: Exception) {
                    println("Error while sending: " + e.localizedMessage)
                    return
                }
            }
        }
    }

    suspend fun DefaultClientWebSocketSession.outputMessages() {
        try {
            for (message in incoming) {
                message as? Frame.Text ?: continue
                messages.add(Message("now", message.readText(), message.readText()))

            }
        } catch (e: Exception) {
            println("Error while receiving: " + e.localizedMessage)
        }
    }

    fun send(data: String) {
        GlobalScope.launch(Dispatchers.IO) {
            queueSendMessage.send(data)
        }
    }

    fun connectChat() {
        GlobalScope.launch(Dispatchers.IO) {
            App.client.webSocket(
                method = HttpMethod.Get,
                host = App.volachat_server_ip,
                port = App.volachat_server_port,
                path = "/chat",
                request = {
                    header("username", App.token!!.username)
                }) {
                val sendRoutine = launch { sendMessage() }
                val receiveRoutine = launch { outputMessages() }

                receiveRoutine.join()
                sendRoutine.cancelAndJoin()
            }
        }
    }

    fun logOut(){
        _isTryingToLogOut.value = true
        App.token = null
        App.client.close()
        println("Connection closed. Goodbye!")
    }
}