package com.example.pracainynierska.ui_view_components.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pracainynierska.view_model.BoosterViewModel


@RequiresApi(Build.VERSION_CODES.O)
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
    }else
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color(0x14FFFFFF),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(4.dp)
        )
}
