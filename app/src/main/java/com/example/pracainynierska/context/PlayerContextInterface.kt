package com.example.pracainynierska.context

import com.example.pracainynierska.API.model.Player
import com.example.pracainynierska.API.model.PlayerStatistics

interface PlayerContextInterface {
    fun getToken(): String?

    fun setToken(token: String)

    fun getPlayer(): Player?

    fun setPlayer(player: Player)

    fun getPlayerStatistics(): PlayerStatistics
}
