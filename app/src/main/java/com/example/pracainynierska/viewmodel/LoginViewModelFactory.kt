package com.example.pracainynierska.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.repository.UserRepository
import com.example.pracainynierska.viewmodel.LoginViewModel

// Fabryka ViewModeli, która dostarcza LoginViewModel z odpowiednim repozytorium użytkownika
class LoginViewModelFactory(
    // Repozytorium użytkownika, które będzie używane w LoginViewModel
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    // Funkcja tworząca instancje ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Sprawdzenie, czy podana klasa to LoginViewModel
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            // Zwraca instancję LoginViewModel z podanym repozytorium użytkownika
            return LoginViewModel(userRepository) as T
        }
        // W przypadku nieznanej klasy ViewModel, rzuca wyjątek
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
