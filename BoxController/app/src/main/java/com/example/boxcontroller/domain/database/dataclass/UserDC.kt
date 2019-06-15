package com.example.boxcontroller.domain.database.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDC (
    @PrimaryKey val id: Int,
    var name: String,
    var email: String,
    var token: String?
)