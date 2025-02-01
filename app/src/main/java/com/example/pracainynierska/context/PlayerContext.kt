package com.example.pracainynierska.context

import com.example.pracainynierska.API.model.Augment
import com.example.pracainynierska.API.model.Category
import com.example.pracainynierska.API.model.Player
import com.example.pracainynierska.API.model.PlayerStatistics
import com.example.pracainynierska.API.model.Statistic

class PlayerContext : PlayerContextInterface {

    private var token: String? = null
    private var player: Player? = null

    override fun getPlayerStatistics(): PlayerStatistics {
        return getPlayer().playerStatistics
    }

    override fun getPlayerAugments(): List<Augment> {
        return getPlayer().activeAugments
    }

    override fun getPlayerCategories(): List<Category> {
        return getPlayer().categories
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

    override fun addPlayerCategory(category: Category) {
        val categories = this.player?.categories ?: mutableListOf()
        categories.add(category)

        this.player?.categories = categories
    }

    override fun editPlayerCategory(updatedCategory: Category) {
        val categories = this.player?.categories ?: mutableListOf()
        val categoryIndex = categories.indexOfFirst { it.id == updatedCategory.id }

        if (categoryIndex != -1) {
            categories[categoryIndex] = updatedCategory
            this.player?.categories = categories
        } else {
            throw Exception("Category with id ${updatedCategory.id} not found")
        }
    }

    override fun removePlayerCategory(categoryId: Int) {
        val categoryToRemove = this.player?.categories?.find { it.id == categoryId }

        if (categoryToRemove != null) {
            this.player?.categories?.remove(categoryToRemove)
        } else {
            throw Exception("Category with ID $categoryId not found")
        }
    }

    override fun addPlayerStatistic(statistic: Statistic) {
        val statistics = this.player?.playerStatistics?.statistics ?: mutableListOf()
        statistics.add(statistic)

        this.player?.playerStatistics = PlayerStatistics(statistics)
    }

    override fun editPlayerStatistic(updatedStatistic: Statistic) {
        val statistics = this.player?.playerStatistics?.statistics ?: mutableListOf()
        val statIndex = statistics.indexOfFirst { it.id == updatedStatistic.id }

        if (statIndex != -1) {
            statistics[statIndex] = updatedStatistic
            this.player?.playerStatistics?.statistics = statistics

            // Update in categories
            this.player?.categories?.forEach { category ->
                val categoryStatIndex = category.statistics.indexOfFirst { it.id == updatedStatistic.id }
                if (categoryStatIndex != -1) {
                    category.statistics[categoryStatIndex] = updatedStatistic
                }
            }
        } else {
            throw Exception("Statistic with id ${updatedStatistic.id} not found")
        }
    }

    override fun removePlayerStatistic(statisticId: Int) {
        val statisticToRemove = this.player?.playerStatistics?.statistics?.find { it.id == statisticId }

        if (statisticToRemove != null) {
            this.player?.playerStatistics?.statistics?.remove(statisticToRemove)

            // Remove in categories
            this.player?.categories?.forEach { category ->
                category.statistics.removeIf { it.id == statisticId }
            }
        } else {
            throw Exception("Statistic with ID $statisticId not found")
        }
    }

    override fun addPlayerAugment(augment: Augment) {
        val augments = this.player?.activeAugments ?: mutableListOf()
        augments.add(augment)

        this.player?.activeAugments = augments
    }

    override fun setPlayerBalance(balance: Int) {
        this.player?.balance = balance
    }

}