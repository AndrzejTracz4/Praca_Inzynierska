package com.example.pracainynierska.ui_view_components.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.NavController
import com.example.pracainynierska.API.model.Category
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.StatGradient
import com.example.pracainynierska.dictionary.StatIcon
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.view_model.CategoryViewModel

@Composable
fun CategoryItem(category: Category, navController: NavController, viewModel: CategoryViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x19FFFFFF), shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category.name,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        navController.navigate("${ViewRoutes.EDITCATEGORY.viewName}/${category.id}")
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = stringResource(R.string.icon_edit_category_description),
                        tint = Color.White.copy(alpha = 0.5f)
                    )
                }
            }

            Column {
                category.statistics.forEachIndexed { index, statistic ->
                    val gradient = StatGradient.getGradientByIndex(index).gradient

                    GradientStatProgressBar(
                        iconResId = StatIcon.fromIconPath(statistic.iconPath)?.rawResId ?: R.raw.intelligence_bar,
                        name = statistic.name,
                        experience = statistic.experience ?: 0,
                        gradient = gradient
                    )

                    Log.d("CategoryItem", "Statistic: $statistic")

                    if (index < category.statistics.size - 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
