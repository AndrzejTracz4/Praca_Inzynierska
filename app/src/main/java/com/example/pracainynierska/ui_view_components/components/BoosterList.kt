package com.example.pracainynierska.ui_view_components.components


import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.example.pracainynierska.view_model.BoosterViewModel


@Composable
fun BoosterList(boosterViewModel: BoosterViewModel) {
    val boostersList = boosterViewModel.boosters.observeAsState(initial = emptyList()).value
    var currentBoosterIndex by remember { mutableStateOf(0) }

    if (boostersList.isNotEmpty()) {
        val currentBooster = boostersList[currentBoosterIndex]
        BoosterCard(
            booster = currentBooster,
            showNext = currentBoosterIndex < boostersList.size - 1,
            showPrevious = currentBoosterIndex > 0,
            onClickNext = {
                if (currentBoosterIndex < boostersList.size - 1) {
                    currentBoosterIndex++
                }
            },
            onClickPrevious = {
                if (currentBoosterIndex > 0) {
                    currentBoosterIndex--
                }
            }
        )
    }
}
