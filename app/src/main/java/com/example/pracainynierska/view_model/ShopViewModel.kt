package com.example.pracainynierska.view_model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import com.example.pracainynierska.API.handler.authorization.AuthorizationHandlerInterface
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.model.Booster
import java.time.LocalDate

class ShopViewModel(
    pc: PlayerContextInterface,
    private val playerAuthorizationHandler: AuthorizationHandlerInterface,
    private val boosterViewModel: BoosterViewModel
) : AbstractViewModel(pc) {

    private var nextBoosterId = 1

    var boostersList = mutableStateOf<List<Booster>>(emptyList())

    init {
        boosterViewModel.boosters.observeForever { boosters ->
            boostersList.value = boosters
            nextBoosterId = (boosters.maxOfOrNull { it.id } ?: 0) + 1
        }
    }

    fun calculateCost(
        shopMode: String,
        duration: Int,
        multiplier: Int
    ): Int {
        return when (shopMode) {
            "Osłona antyredukcyjna statystyk" -> (duration/10) * 2
            "Modyfikator czasowy" -> (duration/10) * 2 * (multiplier/10)
            else -> 0
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun buyBooster(
        shopMode: String,
        category: String,
        duration: Int,
        multiplier: Int,
        price: Int
    ): Boolean {
        if (!canAfford(price)) return false

        val booster = Booster(
            id = nextBoosterId,
            shopMode = shopMode,
            category = category,
            multiplier = if (shopMode == "Modyfikator czasowy") (multiplier/10) else 1,
            duration = (duration/10),
            price = price,
            isActive = true,
            startDate = LocalDate.now()
        )

        boosterViewModel.addBooster(booster)
        nextBoosterId++

        return true
    }

    private fun canAfford(price: Int): Boolean {
        val currentPlayer = playerAuthorizationHandler.getCurrentPlayer() ?: return false
        return currentPlayer.balance >= price
    }
}
