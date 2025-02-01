package com.example.pracainynierska.ui_view_components.components

import android.os.Build
import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.types.AugmentTypes
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.pracainynierska.API.model.Augment
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AugmentCard(
    augment: Augment,
) {

    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
    val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")

    var endDateFormatted = ""

    try {
        val startDateTime = ZonedDateTime.parse(augment.createdAt, inputFormatter)
        val localStartDateTime = startDateTime.withZoneSameInstant(ZoneId.systemDefault())
        val endDateTime = localStartDateTime.plusDays(augment.validForDays.toLong())
        endDateFormatted = endDateTime.format(outputFormatter)
    } catch (e: Exception) {
        Log.e("DateParsing", "Error parsing date: ${e.message}")
        endDateFormatted = "---"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0x14FFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 4.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (augment.type == AugmentTypes.SHIELD) {
                Icon(
                    painter = painterResource(R.drawable.shield),
                    contentDescription = stringResource(R.string.icon_booster_description),
                    tint = Color.White,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterVertically)
                        .padding(10.dp)
                )
            } else if (augment.type == AugmentTypes.BOOSTER) {
                Icon(
                    painter = painterResource(R.drawable.timeout),
                    contentDescription = stringResource(R.string.icon_booster_description),
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
                    text = augment.categoryName,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .background(
                            Color(0x14FFFFFF),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))
                if (augment.type == AugmentTypes.BOOSTER) {
                    Text(
                        text = stringResource(R.string.multiplier_variable, augment.multiplier),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = stringResource(R.string.end_date_variable, endDateFormatted),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}