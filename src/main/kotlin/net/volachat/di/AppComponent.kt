package net.volachat.di

import net.volachat.ui.feature.main.MainScreenComponent
import net.volachat.ui.feature.splash.SplashScreenComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        // Add your modules here
    ]
)
interface AppComponent {
    fun inject(splashScreenComponent: SplashScreenComponent)
    fun inject(mainScreenComponent: MainScreenComponent)
}