package com.example.pracainynierska.database
import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.example.pracainynierska.model.User

// Definiuje bazę danych Room, która przechowuje obiekty typu User
@Database(
    version = 1, // Wersja bazy danych, istotna przy migracjach
    entities = [User::class], // Lista encji (tabel), które są częścią tej bazy danych
    exportSchema = false // Jeśli true, eksportuje schemat bazy danych jako plik JSON (pomocne przy migracjach)
)
abstract class UserDatabase: RoomDatabase() {

    // Abstrakcyjna funkcja, która zwraca DAO (Data Access Object) do wykonywania operacji na tabeli User
    abstract val dao: UserDao

    companion object {
        // Przechowuje singleton instancji bazy danych, aby zapobiec wielokrotnemu tworzeniu obiektów bazy danych
        @Volatile
        private var INSTANCE: UserDatabase? = null

        // Funkcja zapewniająca dostęp do singletonowej instancji bazy danych
        fun getDatabase(context: Context): UserDatabase {
            // Sprawdza, czy instancja bazy danych już istnieje. Jeśli nie, tworzy ją w sposób thread-safe
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Aplikacyjny kontekst, aby baza danych była dostępna w całej aplikacji
                    UserDatabase::class.java, // Klasa reprezentująca bazę danych
                    "user_database" // Nazwa pliku bazy danych
                )
                    .fallbackToDestructiveMigration() // Automatycznie usuwa starą wersję bazy danych w przypadku problemów z migracją
                    .build()
                instance.openHelper.writableDatabase // Otwiera bazę danych do zapisu i utrzymuje ją otwartą podczas działania aplikacji
                INSTANCE = instance // Przypisuje instancję singletona
                instance
            }
        }
    }
}