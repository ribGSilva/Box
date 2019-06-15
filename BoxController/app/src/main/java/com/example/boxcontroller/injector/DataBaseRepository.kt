package com.example.boxcontroller.injector

import android.app.Application
import com.example.boxcontroller.domain.database.UserDatabase
import com.example.boxcontroller.domain.database.dao.UserDao
import com.example.boxcontroller.domain.database.dataclass.UserDC
import dagger.Module
import dagger.Provides
import org.jetbrains.anko.doAsync
import javax.inject.Inject
import javax.inject.Singleton

@Module
class DataBaseRepository @Inject constructor(application: Application) {

    private val userDao: UserDao
    var user: UserDC? = null

    init {
        val database = UserDatabase.getDatabase(application)
        userDao = database.userDao()
        doAsync { user = userDao.getUser() }
    }

    @Provides
    @Singleton
    fun provideUserDao() = userDao

    @Provides
    fun provideUser(dao: UserDao): UserDC {
        if (user == null)
            doAsync { user = dao.getUser() }

        return if (user == null)
            UserDC(-1, "", "", null)
        else
            user!!
    }
}