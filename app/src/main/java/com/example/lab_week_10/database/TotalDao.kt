package com.example.lab_week_10.database

import androidx.room.*

@Dao
interface TotalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(total: Total)

    @Update
    suspend fun update(total: Total)

    @Query("SELECT * FROM total_table WHERE id = :id")
    suspend fun getTotal(id: Long): Total?

    @Query("SELECT COUNT(*) FROM total_table WHERE id = :id")
    suspend fun countTotal(id: Long): Int
}