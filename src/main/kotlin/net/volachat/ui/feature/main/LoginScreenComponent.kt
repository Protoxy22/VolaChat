package net.volachat.ui.feature.main

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import net.volachat.di.AppComponent
import net.volachat.ui.navigation.Component
import javax.inject.Inject

class LoginScreenComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onLoginSuccess: () -> Unit
) : Component, ComponentContext by componentContext {
    @Inject
    lateinit var viewModel: LoginViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }

        val isLoginSuccessed by viewModel.isloginSuccessed.collectAsState()

        if (isLoginSuccessed) {
            onLoginSuccess()
        }

        MainScreen(viewModel)
    }
}