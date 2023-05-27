package com.example.wmn.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wmn.roomDB.Username

@Dao
interface UsernameDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: Username)

    @Delete
    fun deleteUser(user :Username)

    @Update
    fun updateUser(user :Username)

    @Query("Select * from users")
    fun getUser(): List<Username>

}