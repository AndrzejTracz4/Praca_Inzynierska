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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.ui_view_components.components.CustomCreateCategoryButton
import com.example.pracainynierska.ui_view_components.components.TaskTextField
import com.example.pracainynierska.view_model.AddCategoryViewModel

class AddCategoryView(viewModel: AddCategoryViewModel,
                      navController: NavController,
) : AbstractView(viewModel, navController) {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(
        innerPadding: PaddingValues
    ) {

        var categoryName by remember { mutableStateOf("") }
        var searchQuery by remember { mutableStateOf("") }
        var isCategoryValid by remember { mutableStateOf(false) }
        var isStatsValid by remember { mutableStateOf(false) }
        var activeDialogIndex by remember { mutableStateOf(-1) }
        val showAlert = remember { mutableStateOf(false) }
        val showSuccessAlert = remember { mutableStateOf(false) }
        val selectedStats = remember { mutableStateListOf<String?>(null, null, null, null) }

        val availableStats = remember {
            mutableStateListOf(
                "5", "6", "7", "8",
                "Cierpliwość", "Kreatywność", "Zdolności przywódcze",
                "Zarządzanie stresem", "Adaptacja do zmian", "Komunikacja",
                "Praca zespołowa", "Rozwiązywanie problemów", "Innowacyjność",
                "Elastyczność", "Zarządzanie czasem"
            )
        }

        fun validateCategory() {
            isCategoryValid = categoryName.isNotEmpty()
            isStatsValid = selectedStats.any { it != null }
        }

        LaunchedEffect(categoryName) {
            isCategoryValid = categoryName.isNotEmpty()
        }

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

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = stringResource(R.string.name),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0x4DFFFFFF)),
                    contentAlignment = Alignment.Center
                ) {
                    TaskTextField(
                        string = categoryName,
                        onStringChange = { categoryName = it },
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.select_statistics),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                selectedStats.forEachIndexed { index, selectedStat ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(vertical = 4.dp)
                            .background(
                                color = Color(0x19FFFFFF),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { activeDialogIndex = index }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = getStatisticText(selectedStat, index),
                                color = if (selectedStat == null) Color(0xFFbdc3c7) else Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )

                            if (selectedStat != null) {
                                IconButton(
                                    onClick = {
                                        selectedStats[index] = null
                                        validateCategory()
                                    },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = stringResource(R.string.icon_statistic_clear_description),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                if (activeDialogIndex != -1) {
                    AlertDialog(
                        onDismissRequest = {
                            activeDialogIndex = -1
                            searchQuery = ""
                        },
                        title = { Text(stringResource(R.string.select_statistic_label)) },
                        text = {
                            Column {
                                TextField(
                                    value = searchQuery,
                                    onValueChange = { searchQuery = it },
                                    label = { Text(stringResource(R.string.search_statistic)) },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                val filteredStats = availableStats.filter {
                                    it.contains(
                                        searchQuery,
                                        ignoreCase = true
                                    ) && !selectedStats.contains(it)
                                }

                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 200.dp)
                                ) {
                                    itemsIndexed(filteredStats) { index, stat ->
                                        TextButton(
                                            onClick = {
                                                selectedStats[activeDialogIndex] = stat
                                                activeDialogIndex =
                                                    -1
                                                searchQuery =
                                                    ""
                                                validateCategory()
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(stat, color = Color.Black)
                                        }
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    activeDialogIndex = -1
                                    searchQuery = ""
                                }
                            ) {
                                Text(stringResource(R.string.Close))
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            CustomCreateCategoryButton(
                onCreateClick = {
                    if (false == viewModel is AddCategoryViewModel) {
                        throw IllegalStateException("Invalid view model type")
                    }
                    val list = ArrayList(selectedStats.filterNotNull())
                    viewModel.addCategory(categoryName, list)
                },
                isValid = isCategoryValid && isStatsValid,
                isCategoryValid = isCategoryValid,
                isStatsValid = isStatsValid,
                showAlert = showAlert,
                showSuccessAlert = showSuccessAlert,
                alertMessage = when {
                    !isCategoryValid -> stringResource(R.string.validation_invalid_category)
                    !isStatsValid -> stringResource(R.string.validation_empty_statistics)
                    else -> ""
                },
                successMessage = stringResource(R.string.success_category_create)
            )

            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}

@Composable
fun getStatisticText(selectedStat: String?, index: Int): String {
    return selectedStat?.ifEmpty {
        stringResource(R.string.select_statistic, index + 1)
    } ?: stringResource(R.string.select_statistic, index + 1)
}