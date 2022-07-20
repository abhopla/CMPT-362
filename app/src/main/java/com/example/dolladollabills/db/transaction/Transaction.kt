package com.example.dolladollabills.db.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "category_id_column")
    var category_id: Long = 0L,

    @ColumnInfo(name = "milliseconds_column")
    var milliseconds: Long = 0L,

    @ColumnInfo(name = "amount_column")
    var amount: Long = 0L,

    @ColumnInfo(name = "description_column")
    var description: String = ""

)