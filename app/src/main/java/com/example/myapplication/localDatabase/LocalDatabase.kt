package com.example.myapplication.localDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.homePage.model.Data

@Database(entities = [Data::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun DataDao(): DataDao

    companion object {
        @Volatile
        private var instance: LocalDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            LocalDatabase::class.java, "demo.db"
        )
            .build()
    }
}