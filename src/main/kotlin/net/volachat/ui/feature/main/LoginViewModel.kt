package net.volachat.ui.feature.main

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import net.volachat.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import net.volachat.App
import net.volachat.ui.feature.panel.PanelViewModel
import net.voltachat.server.models.auth.UserCredentials
import net.voltachat.server.models.token.TokenResponse
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    val client = HttpClient() {
        engine {
            threadsCount = 4
            pipelining = true
        }
        install(ContentNegotiation) {
            gson()
        }
    }

    private val _isloginSuccessed = MutableStateFlow(false)
    val isloginSuccessed: StateFlow<Boolean> = _isloginSuccessed

    var isBadLogin = false;

    fun onLogin(username: String, password: String) {
        System.out.println("Attempt login for ($username, $password)")

        runBlocking {
            val response: HttpResponse = client.post("http://${App.volachat_server_ip}:${App.volachat_server_port}/login") {
                contentType(ContentType.Application.Json)
                setBody(UserCredentials(username, password))
            }
            if(response.status == HttpStatusCode.Unauthorized){
                isBadLogin = true
            } else {
                val token: TokenResponse = response.body()
                App.token = token
                println("Logged as ${App.token.username}")
                println(App.token.accessToken)
                _isloginSuccessed.value = true
            }
        }
    }

    fun onRegister(username: String, password: String) {
        runBlocking {
            val response: HttpResponse =
                client.post("http://${App.volachat_server_ip}:${App.volachat_server_port}/register") {
                    contentType(ContentType.Application.Json)
                    setBody(UserCredentials(username, password))
                }
            if(response.status == HttpStatusCode.OK){
                println("Registered user ($username, $password)")
            }
        }
    }
}