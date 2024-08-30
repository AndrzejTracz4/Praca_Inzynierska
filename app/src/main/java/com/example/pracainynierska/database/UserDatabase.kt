package com.example.pracainynierska.database
import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.example.pracainynierska.model.User
@Database(
    version = 1,
    entities = [User::class],
    exportSchema = false
)
abstract class UserDatabase: RoomDatabase() {

    abstract val dao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration() //when database have problem with migration automatically delete old version fo database and build new one
                    .build()
                instance.openHelper.writableDatabase //function that keeps open database while app running
                INSTANCE = instance
                instance
            }
        }
    }
}