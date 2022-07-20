package com.example.dolladollabills.db.budget

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_table")
data class Budget (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "expenses_column")
    var expenses: Long = 0L,

    @ColumnInfo(name = "income_column")
    var income: Long = 0L,

)