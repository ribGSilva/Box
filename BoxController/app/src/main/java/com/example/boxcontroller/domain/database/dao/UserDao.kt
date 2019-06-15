package com.example.boxcontroller.domain.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.boxcontroller.domain.database.dataclass.UserDC

@Dao
interface UserDao {

    @Query(" SELECT * FROM user ORDER BY id DESC LIMIT 1 ") fun getUser(): UserDC

    @Insert
    fun insertUser(user: UserDC)

    @Update
    fun updateUser(user: UserDC)

    @Query(" DELETE FROM user ")
    fun deleteAll()

}