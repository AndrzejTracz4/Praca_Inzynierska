package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pracainynierska.R

@Composable
fun CustomCreateCategoryButton(
    onCreateClick: () -> Unit,  // Funkcja wywołania zwrotnego po kliknięciu
    isValid: Boolean,          // Stan walidacji
    isCategoryValid: Boolean,  // Walidacja dla nazwy kategorii
    isStatsValid: Boolean,     // Walidacja dla statystyk
    showAlert: MutableState<Boolean>, // Stan alertu
    showSuccessAlert: MutableState<Boolean>, // Stan alertu sukcesu
    alertMessage: String,       // Wiadomość alertu
    successMessage: String     // Wiadomość sukcesu
) {
    Box(
        modifier = Modifier
            .height(75.dp)
            .padding(vertical = 4.dp)
            .background(
                color = Color(0x19FFFFFF), // Zmiana koloru w zależności od walidacji
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                if (isCategoryValid && isStatsValid) {
                    onCreateClick() // Tylko jeśli walidacja jest prawidłowa
                    showSuccessAlert.value = true // Pokazanie alertu sukcesu po kliknięciu
                } else {
                    // Ustawienie alertu w przypadku błędnej walidacji
                    showAlert.value = true
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize() // Rozciągamy Row na całą szerokość Boxa
        ) {
            Icon(
                painter = painterResource(id = R.drawable.plus_square),
                contentDescription = "Plus icon",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )

            Text(
                text = "Utwórz",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }

    // AlertDialog do obsługi błędu
    if (showAlert.value) {
        AlertDialog(
            onDismissRequest = { showAlert.value = false },  // Zamknięcie dialogu
            title = { Text("Błąd walidacji") },
            text = { Text(alertMessage) },
            confirmButton = {
                TextButton(
                    onClick = { showAlert.value = false }  // Zamknięcie dialogu po kliknięciu "OK"
                ) {
                    Text("OK")
                }
            }
        )
    }

    // AlertDialog do obsługi sukcesu
    if (showSuccessAlert.value) {
        AlertDialog(
            onDismissRequest = { showSuccessAlert.value = false },  // Zamknięcie dialogu
            title = { Text("Sukces!") },
            text = { Text(successMessage) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessAlert.value = false // Zamknięcie alertu sukcesu po kliknięciu "OK"
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}
