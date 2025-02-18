package com.example.pracainynierska.ui_view_components.components

import DailyTaskDetailsButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LiveData
import com.example.pracainynierska.API.model.Challenge
import com.example.pracainynierska.R
import com.example.pracainynierska.API.model.Task

@Composable
fun DailyTaskDetailsDialog(challenge: Challenge, status: String, onAccept: () -> Unit, onExecute: () -> Unit, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.task_details),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xDD1C1C1C),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${challenge.points} exp",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(
                                    Color(0x14FFFFFF),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 2.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(
                                    Color(0x14FFFFFF),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = challenge.coins.toString(),
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Icon(
                                painter = painterResource(id = R.drawable.coins),
                                contentDescription = stringResource(R.string.icon_balance_description),
                                tint = Color.Unspecified,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Text(
                        text = challenge.name,
                        color = Color(0xFF3CB043),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = challenge.description,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (status == "unknown") {
                            DailyTaskDetailsButton(
                                text = stringResource(R.string.accept),
                                color = Color(0xFF3CB043),
                                onClick = { onAccept() },
                                modifier = Modifier.weight(1f)
                            )

                            DailyTaskDetailsButton(
                                text = stringResource(R.string.cancel),
                                color = Color(0xFFFF0000),
                                onClick = onDismiss,
                                modifier = Modifier.weight(1f)
                            )
                        } else if(status == "accepted") {
                            DailyTaskDetailsButton(
                                text = stringResource(R.string.execute),
                                color = Color(0xFF3CB043),
                                onClick = { onExecute() },
                                modifier = Modifier.weight(1f)
                            )

                            DailyTaskDetailsButton(
                                text = stringResource(R.string.cancel),
                                color = Color(0xFFFF0000),
                                onClick = onDismiss,
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            DailyTaskDetailsButton(
                                text = stringResource(R.string.cancel),
                                color = Color(0xFFFF0000),
                                onClick = onDismiss,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}
