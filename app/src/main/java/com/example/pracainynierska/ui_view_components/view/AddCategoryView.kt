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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.ui_view_components.components.CustomCreateCategoryButton
import com.example.pracainynierska.ui_view_components.components.TaskTextField
import com.example.pracainynierska.view_model.LoginViewModel

class AddCategoryView(loginViewModel: LoginViewModel,
                      navController: NavController,
) : AbstractView(loginViewModel, navController) {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(
        innerPadding: PaddingValues
    ) {

        var categoryName by remember { mutableStateOf("") }
        var searchQuery by remember { mutableStateOf("") } // Zmienna dla wyszukiwania
        var isCategoryValid by remember { mutableStateOf(false) }
        var isStatsValid by remember { mutableStateOf(false) }
        var activeDialogIndex by remember { mutableStateOf(-1) }
        val showAlert = remember { mutableStateOf(false) }  // Przechowywanie stanu alertu
        val showSuccessAlert = remember { mutableStateOf(false) }  // Stan alertu sukcesu
        val selectedStats = remember { mutableStateListOf<String?>(null, null, null, null) } // Na początku 4 puste pola (null)

        val availableStats = remember {
            mutableStateListOf(
                "Determinacja", "Sprawność fizyczna", "Inteligencja", "Wiedza",
                "Cierpliwość", "Kreatywność", "Zdolności przywódcze",
                "Zarządzanie stresem", "Adaptacja do zmian", "Komunikacja",
                "Praca zespołowa", "Rozwiązywanie problemów", "Innowacyjność",
                "Elastyczność", "Zarządzanie czasem"
            )
        }

        fun validateCategory() {
            // Walidacja nazwy kategorii
            isCategoryValid = categoryName.isNotEmpty()
            // Walidacja statystyk
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
                .verticalScroll(rememberScrollState()) // Włączamy przewijanie
        ) {
            Spacer(modifier = Modifier.height(55.dp))

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Nazwa",
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
                    text = "Wybierz statystyki",
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
                                text = selectedStat?.ifEmpty { "Wybierz statystykę ${index + 1}" } ?: "Wybierz statystykę ${index + 1}",
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
                                        imageVector = Icons.Default.Clear, // Ikona do wyczyszczenia
                                        contentDescription = "Wyczyść statystykę",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                // Dialog do wyboru statystyki
                if (activeDialogIndex != -1) {
                    AlertDialog(
                        onDismissRequest = {
                            activeDialogIndex = -1
                            searchQuery = ""
                        },
                        title = { Text("Wybierz statystykę") },
                        text = {
                            Column {
                                TextField(
                                    value = searchQuery,
                                    onValueChange = { searchQuery = it },
                                    label = { Text("Szukaj statystyki") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Filtrowana lista statystyk
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
                                                    -1  // Zamknij dialog po wyborze
                                                searchQuery =
                                                    ""        // Wyczyść zapytanie wyszukiwania
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
                                Text("Zamknij")
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            CustomCreateCategoryButton(
                onCreateClick = {
                    // Dodatkowa logika przy tworzeniu kategorii
                },
                isValid = isCategoryValid && isStatsValid, // Stan walidacji
                isCategoryValid = isCategoryValid,
                isStatsValid = isStatsValid,
                showAlert = showAlert,  // Przekazywanie stanu alertu błędu
                showSuccessAlert = showSuccessAlert,  // Przekazywanie stanu alertu sukcesu
                alertMessage = when {
                    !isCategoryValid -> "Nazwa kategorii nie może być pusta."
                    !isStatsValid -> "Wybierz przynajmniej jedną statystykę."
                    else -> ""
                },
                successMessage = "Kategoria została pomyślnie utworzona!" // Wiadomość sukcesu
            )

            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}
