package com.example.boxcontroller.domain.database

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.boxcontroller.domain.database.dao.UserDao
import com.example.boxcontroller.domain.database.dataclass.UserDC
import org.jetbrains.anko.doAsync

@Database(version = 1, entities = [UserDC::class], exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var instance: UserDatabase? = null

        private val userDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                instance?.let{ roomDB ->
                    doAsync {
                        val dao = roomDB.userDao()

                        dao.deleteAll()

                        dao.insertUser(UserDC(1, "Usuario Teste", "email@usuario.com", ""))
                    }
                }
            }
        }

        fun getDatabase(context: Context) : UserDatabase {
            if (instance == null) {
                synchronized(UserDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database")
                        .addCallback(userDatabaseCallback)
                        .build()
                }
            }

            return instance!!
        }
    }
}