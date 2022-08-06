package com.example.dolladollabills.db.goals

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDatabaseDao {

    @Insert
    suspend fun insertGoals(goal: Goal)

    @Query("SELECT * FROM goals_table")
    fun getAllGoals(): Flow<List<Goal>>

    @Query("DELETE FROM goals_table WHERE id = :key")
    suspend fun deleteGoal(key: Long)
}