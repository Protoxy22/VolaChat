package net.volachat.di

import net.volachat.ui.feature.main.LoginScreenComponent
import net.volachat.ui.feature.splash.SplashScreenComponent
import dagger.Component
import net.volachat.ui.feature.panel.PanelScreenComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        // Add your modules here
    ]
)
interface AppComponent {
    fun inject(splashScreenComponent: SplashScreenComponent)
    fun inject(loginScreenComponent: LoginScreenComponent)
    fun inject(panelScreenComponent: PanelScreenComponent)
}