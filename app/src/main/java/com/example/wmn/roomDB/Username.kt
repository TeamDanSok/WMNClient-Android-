package com.example.wmn.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Username(
    @PrimaryKey var uId:Int
)
