package com.example.pracainynierska.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.pracainynierska.model.User

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: User)
    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
    suspend fun getUser(username:String, password:String):User?

    @Query("SELECT * FROM user_table WHERE id = :userId")
    suspend fun getUserById(userId: Long): User?

    @Query("SELECT * FROM user_table WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}