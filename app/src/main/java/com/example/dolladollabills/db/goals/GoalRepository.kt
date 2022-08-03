package com.example.dolladollabills.db.goals


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GoalRepository(private val goalDatabaseDao: GoalDatabaseDao) {
    val allGoals : Flow<List<Goal>> =
        goalDatabaseDao.getAllGoals()

    fun insert(goal: Goal){
        CoroutineScope(Dispatchers.IO).launch{
            goalDatabaseDao.insertGoals(goal)
        }
    }


    fun delete(id: Long){
        CoroutineScope(Dispatchers.IO).launch {
            goalDatabaseDao.deleteGoal(id)
        }
    }
}