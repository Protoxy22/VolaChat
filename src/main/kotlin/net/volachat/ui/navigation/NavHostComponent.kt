package net.volachat.ui.navigation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfadeScale
import com.arkivanov.decompose.replaceCurrent
import com.arkivanov.decompose.router
import com.arkivanov.decompose.statekeeper.Parcelable
import net.volachat.di.AppComponent
import net.volachat.ui.feature.main.LoginScreenComponent
import net.volachat.ui.feature.panel.PanelScreenComponent
import net.volachat.ui.feature.splash.SplashScreenComponent

/**
 * All navigation decisions are made from here
 */
class NavHostComponent(
    private val componentContext: ComponentContext,
) : Component, ComponentContext by componentContext {

    /**
     * Available screensSelectApp
     */
    private sealed class Config : Parcelable {
        object Splash : Config()
        object Main : Config()
        object Panel : Config()
    }

    private val appComponent: AppComponent = net.volachat.di.DaggerAppComponent
        .create()

    /**
     * Router configuration
     */
    private val router = router<Config, Component>(
        initialConfiguration = Config.Splash,
        childFactory = ::createScreenComponent
    )

    /**
     * When a new navigation request made, the screen will be created by this method.
     */
    private fun createScreenComponent(config: Config, componentContext: ComponentContext): Component {
        return when (config) {
            is Config.Splash -> SplashScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onSplashFinished = ::onSplashFinished,
            )
            Config.Main -> LoginScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onLoginSuccess = ::onLoginSuccess,
            )
            Config.Panel -> PanelScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onLogOut = ::onLogOut,
            )
        }
    }

    @Composable
    override fun render() {
        Children(
            routerState = router.state,
            animation = crossfadeScale()
        ) { child ->
            child.instance.render()
        }
    }

    /**
     * Invoked when splash finish data sync
     */
    private fun onSplashFinished() {
        router.replaceCurrent(Config.Main)
    }

    /**
     * Invoked when login successfully
     */
    private fun onLoginSuccess() {
        router.replaceCurrent(Config.Panel)
    }

    /**
     * Invoked when login successfully
     */
    private fun onLogOut() {
        router.replaceCurrent(Config.Main)
    }
}