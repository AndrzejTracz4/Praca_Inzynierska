package com.example.pracainynierska.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.pracainynierska.model.User

// Interfejs definiujący operacje na bazie danych (DAO - Data Access Object) dla tabeli User
@Dao
interface UserDao {

    // Funkcja służąca do wstawiania nowego rekordu lub aktualizacji istniejącego użytkownika w tabeli user_table
    @Upsert
    suspend fun upsertUser(user: User)

    // Funkcja usuwająca rekord użytkownika z tabeli user_table
    @Delete
    suspend fun deleteUser(user: User)


    // Funkcja zwracająca użytkownika z tabeli user_table na podstawie podanej nazwy użytkownika i hasła.
    // Zwraca użytkownika tylko wtedy, gdy zarówno nazwa użytkownika, jak i hasło pasują do rekordu w tabeli.
    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
    suspend fun getUser(username:String, password:String):User?

    // Funkcja zwracająca użytkownika z tabeli user_table na podstawie jego unikalnego identyfikatora (ID)
    @Query("SELECT * FROM user_table WHERE id = :userId")
    suspend fun getUserById(userId: Long): User?

    // Funkcja zwracająca użytkownika z tabeli user_table na podstawie nazwy użytkownika.
    // Zwraca pierwszy znaleziony rekord (lub `null`, jeśli użytkownik nie istnieje)
    @Query("SELECT * FROM user_table WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    // Funkcja zwracająca użytkownika z tabeli user_table na podstawie adresu e-mail.
    // Zwraca pierwszy znaleziony rekord (lub `null`, jeśli użytkownik nie istnieje)
    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}