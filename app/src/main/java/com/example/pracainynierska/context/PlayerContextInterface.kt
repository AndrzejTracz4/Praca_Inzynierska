package com.example.pracainynierska.context

import com.example.pracainynierska.API.model.Augment
import com.example.pracainynierska.API.model.Category
import com.example.pracainynierska.API.model.Player
import com.example.pracainynierska.API.model.PlayerStatistics
import com.example.pracainynierska.API.model.Statistic
import com.example.pracainynierska.API.model.Task

interface PlayerContextInterface {
    fun getToken(): String?
    fun setToken(token: String)

    fun getPlayer(): Player?
    fun setPlayer(player: Player)

    fun getPlayerStatistics(): PlayerStatistics

    fun getPlayerAugments(): List<Augment>

    fun getPlayerCategories(): List<Category>

    fun addPlayerCategory(category: Category)

    fun editPlayerCategory(updatedCategory: Category)

    fun removePlayerCategory(categoryId: Int)

    fun addPlayerStatistic(statistic: Statistic)

    fun editPlayerStatistic(updatedStatistic: Statistic)

    fun removePlayerStatistic(statisticId: Int)

    fun addPlayerAugment(augment: Augment)

    fun setPlayerBalance(balance: Int)
}