package com.example.myroomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wmn.roomDB.Username

@Database(
    entities = [Username::class],
    version =1
)
abstract class UsernameDatabase:RoomDatabase(){
    abstract fun productDao():UsernameDAO

    companion object {
        private  var INSTANCE: UsernameDatabase? = null

        fun getDatabase(context: Context): UsernameDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            val instance = Room.databaseBuilder(
                context,
                UsernameDatabase::class.java,
                "productdb"
            ).build()

            INSTANCE = instance
            return instance
        }
    }
}