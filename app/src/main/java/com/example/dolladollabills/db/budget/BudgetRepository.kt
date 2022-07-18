package com.example.dolladollabills.db.budget

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BudgetRepository(private val budgetDatabaseDao: BudgetDatabaseDao) {
    val allCategories : Flow<List<Budget>> =
        budgetDatabaseDao.getAllBudgets()

    fun insert(budget: Budget){
        CoroutineScope(Dispatchers.IO).launch{
            budgetDatabaseDao.insertBudget(budget)
        }
    }

    fun delete(id: Long){
        CoroutineScope(Dispatchers.IO).launch {
            budgetDatabaseDao.deleteBudget(id)
        }
    }
}