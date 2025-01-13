package com.example.pracainynierska.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Definiuje encję (tabelę) w bazie danych o nazwie "user_table"
@Entity(tableName = "user_table")
data class User(
    // Unikalny identyfikator użytkownika, który jest kluczem głównym w tabeli
    // autoGenerate = true oznacza, że identyfikator będzie automatycznie generowany przez bazę danych
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    var password: String,
    var email: String,
    var userPhotoPath: String?,
    var level: Int,
    var experience: Float,
    var userUUID: String,
    var determination: Float,
    var physical_fitness: Float,
    var intelligence: Float,
    var knowledge: Float,
)
