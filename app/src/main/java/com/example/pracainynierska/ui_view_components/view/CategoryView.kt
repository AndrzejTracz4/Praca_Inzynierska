package com.example.pracainynierska.ui_view_components.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.StatGradient
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.ui_view_components.components.GradientStatProgressBar
import com.example.pracainynierska.view_model.CategoryViewModel

class CategoryView(
    viewModel: CategoryViewModel,
    navController: NavController
) : AbstractView(viewModel, navController) {


    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(
        innerPadding: PaddingValues
    ) {

        if (false == (viewModel is CategoryViewModel)) {
            throw Exception("Invalid View Model")
        }

        var showSuccessDialog by remember { mutableStateOf(false) }
        val categoryList = viewModel.getPlayerCategories()
        var searchQuery by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF4C0949),
                            Color(0xFF470B93)
                        ),
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(0f, 0f)
                    )
                )
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(55.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomButton(
                    text = stringResource(R.string.add_category),
                    onClick = { navController.navigate(ViewRoutes.ADDCATEGORY.viewName) },
                    iconResId = R.drawable.plus,
                    modifier = Modifier
                        .weight(1f)
                )

                Spacer(modifier = Modifier.width(10.dp))

                CustomButton(
                    text = stringResource(R.string.add_statistic),
                    onClick = { navController.navigate(ViewRoutes.ADDSTATISTIC.viewName) },
                    iconResId = R.drawable.plus,
                    modifier = Modifier
                        .weight(1f)
                )

            }

            if (showSuccessDialog) {
                AlertDialog(
                    onDismissRequest = { showSuccessDialog = false },
                    confirmButton = {
                        Button(onClick = { showSuccessDialog = false }) {
                            Text(stringResource(R.string.ok))
                        }
                    },
                    title = { Text(stringResource(R.string.success)) },
                    text = { Text(stringResource(R.string.success_creation_statistics)) }
                )
            }

            // Search bar
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = {
                    Text(
                        text = stringResource(R.string.search_category),
                        color = Color.White
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White.copy(alpha = 0.2f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            )


            val filteredCategories = if (searchQuery.isNotEmpty()) {
                categoryList.filter { category ->
                    category.name.contains(searchQuery, ignoreCase = true)
                }
            } else {
                categoryList
            }

            filteredCategories.forEach { category ->
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
//                            horizontalArrangement = Arrangement.SpaceBetween,
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
                                    iconResId = viewModel.getPhotoResId(statistic.iconPath),
                                    name = statistic.name,
                                    experience = statistic.experience,
                                    gradient = gradient
                                )

                                if (index < category.statistics.size - 1) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    iconResId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .padding(vertical = 4.dp)
            .background(
                color = Color(0x19FFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
