package com.example.pracainynierska.repository

import androidx.lifecycle.LiveData
import com.example.pracainynierska.database.UserDao
import com.example.pracainynierska.model.User

// Repozytorium użytkowników, które pośredniczy w operacjach na bazie danych, korzystając z UserDao
class UserRepository (private val userDao: UserDao) {

    // Wstawia lub aktualizuje dane użytkownika w bazie danych
    // Funkcja działa asynchronicznie, ponieważ używa 'suspend'(wykonuje się w tle)
    suspend fun upsertUser(user: User) {
        userDao.upsertUser(user)
    }

    // Pobiera użytkownika na podstawie podanego username i hasła
    // Zwraca obiekt User, jeśli użytkownik zostanie znaleziony, w przeciwnym razie zwraca null
    suspend fun getUser(username: String, password: String): User? {
        return userDao.getUser(username, password)
    }

    // Pobiera użytkownika na podstawie podanego username
    // Zwraca obiekt User, jeśli użytkownik zostanie znaleziony, w przeciwnym razie zwraca null
    fun getUserByUsernameLiveData(username: String): LiveData<User> {
        return userDao.getUserByUsernameLiveData(username)
    }

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    // Pobiera użytkownika na podstawie podanego adresu email
    // Zwraca obiekt User, jeśli użytkownik zostanie znaleziony, w przeciwnym razie zwraca null
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    // Metoda do aktualizacji ścieżki zdjęcia użytkownika
    suspend fun updateUserPhotoPath(userId: Int?, photoPath: String) {
        val user = userDao.getUserById(userId)
        if (user != null) {
            user.userPhotoPath = photoPath
            userDao.upsertUser(user)
        }
    }

}