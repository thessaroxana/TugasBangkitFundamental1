package com.dicoding.tugasbangkitfundamental1.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavUserEntity::class], version = 1)

abstract class FavUserDatabase : RoomDatabase() {
    abstract fun favUserDao(): FavUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavUserDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): FavUserDatabase {
            if (INSTANCE == null) {
                synchronized(FavUserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavUserDatabase::class.java,
                        "fav_user_database"
                    )
                        .build()
                }
            }
            return INSTANCE as FavUserDatabase
        }
    }
}
