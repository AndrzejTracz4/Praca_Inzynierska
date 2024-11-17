package com.example.pracainynierska.API

import com.example.pracainynierska.API.model.Player
import okhttp3.OkHttpClient


open class ApiDetails {
    private val apiPath: String = "https://11fa-83-31-162-49.ngrok-free.app"

    protected val apiClient = OkHttpClient()

    private var token: String? = null

    private var player: Player? = null

    init {
        reinitializeApiClient()
    }

    fun reinitializeApiClient() {
        apiClient.newBuilder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        }
    }

    fun getApiPath(): String {
        return apiPath
    }

    fun getToken(): String? {
        return token
    }

    fun setToken(token: String) {
        this.token = token

    }

    fun getPlayer(): Player? {
        return player
    }

    fun setPlayer(player: Player) {
        this.player = player
    }

    fun buildPath(path: String): String {
        return "$apiPath/$path"
    }

    fun authorized(): Boolean {
        return token != null
    }
}