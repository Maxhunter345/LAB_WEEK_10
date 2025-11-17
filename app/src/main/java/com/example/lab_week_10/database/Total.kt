package com.example.lab_week_10.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "total_table")
data class Total(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 1L, // Default ID 1 untuk simplicity

    @ColumnInfo(name = "total_value")
    val totalValue: Int = 0
)