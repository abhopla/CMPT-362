package com.example.dolladollabills.db.goals


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index


@Entity(tableName = "goals_table", indices = [Index(value = ["name_column"], unique = true)])
data class Goal (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "name_column")
    var name: String = "",

    )