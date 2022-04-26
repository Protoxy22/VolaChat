package net.volachat.data.repo

import javax.inject.Inject

class MyRepo @Inject constructor() {
    fun getClickedWelcomeText() = "Hello Desktop!"
}