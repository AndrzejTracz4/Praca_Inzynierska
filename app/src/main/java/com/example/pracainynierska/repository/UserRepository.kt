package com.example.pracainynierska.repository

import com.example.pracainynierska.database.UserDao
import com.example.pracainynierska.model.User

class UserRepository (private val userDao: UserDao) {

    suspend fun upsertUser(user: User) {
        userDao.upsertUser(user)
    }

    suspend fun getUser(username: String, password: String): User? {
        return userDao.getUser(username, password)
    }
}