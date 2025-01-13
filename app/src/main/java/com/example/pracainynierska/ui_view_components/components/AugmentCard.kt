package com.example.pracainynierska.ui_view_components.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.types.ShopTypes
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AugmentCard(
    augment: com.example.pracainynierska.API.model.Augment,
    showNext: Boolean,
    showPrevious: Boolean,
    onClickNext: () -> Unit,
    onClickPrevious: () -> Unit
) {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val startDate = LocalDate.parse(augment.startDate.toString(), formatter)
    val endDate = startDate.plusDays(augment.duration.toLong())
    val endDateFormatted = endDate.format(formatter)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0x14FFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (augment.shopMode == ShopTypes.SHIELD) {
                Icon(
                    painter = painterResource(R.drawable.shield),
                    contentDescription = "BoosterIcon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterVertically)
                        .padding(10.dp)
                )
            }else if (augment.shopMode == ShopTypes.BOOSTER){
                Icon(
                    painter = painterResource(R.drawable.timeout),
                    contentDescription = "BoosterIcon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterVertically)
                        .padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = augment.category,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            Color(0xFF3CB043),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))
                if (augment.shopMode == ShopTypes.BOOSTER) {
                    Text(
                        text = "Mnożnik: x${augment.multiplier}",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Data zakończenia: $endDateFormatted",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                ) {
                    if (showPrevious) {
                        Icon(
                            painter = painterResource(R.drawable.next_previous_arrow),
                            contentDescription = "Previous Booster",
                            modifier = Modifier
                                .size(32.dp)
                                .rotate(180f)
                                .clickable { onClickPrevious() },
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    if (showNext) {
                        Icon(
                            painter = painterResource(R.drawable.next_previous_arrow),
                            contentDescription = "Next Booster",
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { onClickNext() },
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}