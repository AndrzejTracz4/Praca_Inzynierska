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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pracainynierska.API.model.Augment


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AugmentList(augments: List<Augment>) {
    var currentBoosterIndex by remember { mutableStateOf(0) }

        if (augments.isNotEmpty()) {
            val currentBooster = augments[currentBoosterIndex]
            AugmentCard(
                augment = currentBooster,
                showNext = currentBoosterIndex < augments.size - 1,
                showPrevious = currentBoosterIndex > 0,
                onClickNext = {
                    if (currentBoosterIndex < augments.size - 1) {
                        currentBoosterIndex++
                    }
                }
            ) {
                if (currentBoosterIndex > 0) {
                    currentBoosterIndex--
                }
            }
        } else
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
