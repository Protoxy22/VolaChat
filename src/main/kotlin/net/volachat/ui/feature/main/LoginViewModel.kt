package net.volachat.ui.feature.main

import net.volachat.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.volachat.ui.feature.panel.PanelViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    private val _isloginSuccessed = MutableStateFlow(false)
    val isloginSuccessed: StateFlow<Boolean> = _isloginSuccessed

    fun onClickMeClicked(username: String, password: String) {
        System.out.println("Button pushed")
        System.out.println(username)
        System.out.println(password)
        _isloginSuccessed.value = true
    }
}