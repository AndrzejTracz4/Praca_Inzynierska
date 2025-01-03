package com.example.pracainynierska.context

import com.example.pracainynierska.API.model.Player
import com.example.pracainynierska.API.model.PlayerStatistics

class PlayerContext : PlayerContextInterface {

    private var token: String? = null
    private var player: Player? = null

    override fun getPlayerStatistics(): PlayerStatistics {
        return getPlayer().playerStatistics!!
    }

    override fun getToken(): String? {
        return token
    }

    override fun setToken(token: String) {
        this.token = token
    }

    override fun getPlayer(): Player {
        return player ?: throw Exception("Player not set")
    }

    override fun setPlayer(player: Player) {
        this.player = player
    }
}