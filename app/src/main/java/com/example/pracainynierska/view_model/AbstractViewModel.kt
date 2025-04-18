package com.example.pracainynierska.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pracainynierska.API.model.Achievement
import com.example.pracainynierska.API.model.Augment
import com.example.pracainynierska.API.model.Category
import com.example.pracainynierska.API.model.Player
import com.example.pracainynierska.API.model.Statistic
import com.example.pracainynierska.context.PlayerContextInterface

abstract class AbstractViewModel(protected val playerContext: PlayerContextInterface) : ViewModel() {
    private val _player = MutableLiveData<Player>()
    val player: LiveData<Player> get() = _player

    fun getPlayerAchievements(): List<Achievement> {
        return playerContext.getPlayerAchievements()
    }

    fun getPlayerStatistics(): List<Statistic> {
        return playerContext.getPlayerStatistics().statistics
    }

    fun getPlayerAugments(): List<Augment> {
        return playerContext.getPlayerAugments()
    }

    fun getPlayerCategories(): List<Category> {
        return playerContext.getPlayerCategories()
    }

    fun getPlayer(): Player? {
        return playerContext.getPlayer()
    }

    fun getPlayerModel(): LiveData<Player> {
        _player.value = playerContext.getPlayer()

        return player
    }
}