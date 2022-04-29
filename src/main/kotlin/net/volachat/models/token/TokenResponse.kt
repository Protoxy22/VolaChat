package net.volachat.models.token


data class TokenResponse(
    val username: String,
    val accessToken: String,
    val expiresIn: Long
){ constructor() : this("", "", 0) }