package com.example.pracainynierska.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Definiuje encję (tabelę) w bazie danych o nazwie "user_table"
@Entity(tableName = "user_table")
data class User (
    // Unikalny identyfikator użytkownika, który jest kluczem głównym w tabeli
    // autoGenerate = true oznacza, że identyfikator będzie automatycznie generowany przez bazę danych
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    // Nazwa użytkownika, unikalny identyfikator przypisany przez użytkownika
    val username: String,

    // Hasło użytkownika, które przechowujemy jako string
    var password: String,

    // Adres e-mail użytkownika
    val email: String
)