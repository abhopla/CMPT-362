package com.example.dolladollabills.db.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDatabaseDao {

    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transaction_table")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("DELETE FROM transaction_table WHERE id = :key")
    suspend fun deleteTransaction(key: Long)

}