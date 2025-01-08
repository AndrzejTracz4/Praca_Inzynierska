package com.example.pracainynierska.API

import com.example.pracainynierska.API.model.Player
import com.example.pracainynierska.context.PlayerContextInterface
import okhttp3.OkHttpClient


open class ApiDetails(private var playerContext: PlayerContextInterface) {
    private val apiPath: String = "https://cac7-83-31-123-5.ngrok-free.app"

    protected val apiClient = OkHttpClient()

    fun reinitializeApiClient() {
        val token = getToken()
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
        return playerContext.getToken()
    }

    fun setToken(token: String) {
        this.playerContext.setToken(token)
        reinitializeApiClient()
    }

    fun getPlayer(): Player? {
        return playerContext.getPlayer()
    }

    fun setPlayer(player: Player) {
        this.playerContext.setPlayer(player)
    }

    fun buildPath(path: String): String {
        return "$apiPath/$path"
    }

    fun authorized(): Boolean {
        return getToken() != null
    }
}