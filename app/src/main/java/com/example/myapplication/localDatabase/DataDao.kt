package com.example.myapplication.localDatabase

import androidx.room.*
import com.example.myapplication.ui.general.model.Data

@Dao
interface DataDao {
    @Query("SELECT * FROM data")
    suspend fun getData(): List<Data>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: List<Data>)

    @Update
    suspend fun updateData(data: List<Data>)

    @Delete
    suspend fun delete(data: List<Data>)
}