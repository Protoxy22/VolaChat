package net.volachat.ui.feature.main

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import net.volachat.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.volachat.App
import net.volachat.models.auth.UserCredentials
import net.volachat.models.token.TokenResponse
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    private val _isloginSuccessed = MutableStateFlow(false)
    val isloginSuccessed: StateFlow<Boolean> = _isloginSuccessed

    var isBadLogin = false;
    var isBadRegister = false;
    var isSuccessRegister = false;

    fun onLogin(username: String, password: String) {
        System.out.println("Attempt login for ($username, $password)")
        GlobalScope.launch(Dispatchers.IO) {
            val response: HttpResponse =
                App.client.post("http://${App.volachat_server_ip}:${App.volachat_server_port}/login") {
                    contentType(ContentType.Application.Json)
                    setBody(UserCredentials(username, password))
                }
            if (response.status == HttpStatusCode.Unauthorized) {
                isBadLogin = true
                println("User error login")
            } else if (response.status == HttpStatusCode.OK) {
                println(response)
                val token: TokenResponse = response.body()
                App.token = token
                println("Logged as ${App.token!!.username}")
                println(App.token!!.accessToken)
                _isloginSuccessed.value = true
            }
        }
    }

    fun onRegister(username: String, password: String) {
        runBlocking {
            val response: HttpResponse =
                App.client.post("http://${App.volachat_server_ip}:${App.volachat_server_port}/register") {
                    contentType(ContentType.Application.Json)
                    setBody(UserCredentials(username, password))
                }
            if(response.status == HttpStatusCode.Unauthorized) {
                isBadRegister = true
                println("User error register")
            } else if (response.status == HttpStatusCode.OK){
                println("Registered user ($username, $password)")
                isSuccessRegister = true
            }
        }
    }
}