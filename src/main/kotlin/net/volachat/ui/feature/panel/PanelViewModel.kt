package net.volachat.ui.feature.panel

import io.ktor.client.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import net.volachat.App
import net.volachat.util.ViewModel
import javax.inject.Inject

class PanelViewModel @Inject constructor() : ViewModel() {

    val client = HttpClient() {
        engine {
            threadsCount = 4
            pipelining = true
        }
        install(ContentNegotiation) {
            gson()
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(App.token.accessToken, App.token.accessToken)
                }
            }
        }
    }

    fun runConnect() {
    }

    fun onClickMeClicked() {
    }
}