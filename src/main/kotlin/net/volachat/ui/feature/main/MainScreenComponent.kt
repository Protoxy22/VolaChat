package net.volachat.ui.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.ComponentContext
import net.volachat.di.AppComponent
import net.volachat.ui.navigation.Component
import javax.inject.Inject

class MainScreenComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
) : Component, ComponentContext by componentContext {
    @Inject
    lateinit var viewModel: MainViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }

        MainScreen(viewModel)
    }
}