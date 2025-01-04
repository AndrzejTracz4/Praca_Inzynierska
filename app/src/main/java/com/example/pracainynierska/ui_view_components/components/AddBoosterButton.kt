package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pracainynierska.R
import com.example.pracainynierska.model.Booster
import com.example.pracainynierska.model.Task
import com.example.pracainynierska.view_model.BoosterViewModel
import java.time.LocalDate

@Composable
fun AddBoosterButton(
    selectedCategory: String,
    selectedShopMode: String,
    sliderValueTime: Float,
    sliderValueMultiplier: Float,
    costValue: Int,
    boosterViewModel: BoosterViewModel,
    startDate: LocalDate,
    isActive: Boolean
) {
    var boostersList by remember { mutableStateOf(emptyList<Booster>()) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    boosterViewModel.boosters.observeForever { boosters ->
        boostersList = boosters
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(vertical = 4.dp)
            .background(
                color = Color(0x19FFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                val lastBoosterId = boostersList.maxOfOrNull { it.id } ?: 0
                val booster = Booster(
                    id = lastBoosterId + 1,
                    shopMode = selectedShopMode,
                    category = selectedCategory,
                    multiplier = if (selectedShopMode == "Osłona antyredukcjna statystyk") {
                        0f
                    } else {
                        sliderValueMultiplier
                    },
                    duration = sliderValueTime,
                    price = costValue,
                    startDate = startDate,
                    isActive = isActive
                )

                boosterViewModel.addBooster(booster)

                dialogMessage = "Pomyślnie zakupiono booster!"
                showDialog = true
            }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.buy),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Potwierdź",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Sukces!") },
            text = { Text(text = dialogMessage) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}
