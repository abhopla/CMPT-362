package com.example.dolladollabills.db.budget

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDatabaseDao {

    @Insert
    suspend fun insertBudget(category: Budget)

    @Query("SELECT * FROM budget_table")
    fun getAllBudgets(): Flow<List<Budget>>

    @Query("DELETE FROM budget_table WHERE id = :key")
    suspend fun deleteBudget(key: Long)
}