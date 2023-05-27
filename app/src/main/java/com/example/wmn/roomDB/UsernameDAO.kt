package com.example.myroomdb

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
    fun insertProduct(user:Username)

    @Delete
    fun deleteProduct(user:Username)

    @Update
    fun updateProduct(user:Username)

    @Query("Select * from users")
    fun getAllRecord(): List<Username>

    @Query("Select * from users where uID = :uId")
    fun findProduct(uId: Int): List<Username>
}