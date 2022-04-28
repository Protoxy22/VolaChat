package net.volachat.ui.feature.panel

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import net.volachat.di.AppComponent
import net.volachat.ui.navigation.Component
import javax.inject.Inject

class PanelScreenComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
) : Component, ComponentContext by componentContext {
    @Inject
    lateinit var viewModel: PanelViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }

        PanelScreen(viewModel)
    }
}