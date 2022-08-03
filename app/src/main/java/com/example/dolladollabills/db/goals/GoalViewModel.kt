package com.example.dolladollabills.db.goals

import androidx.lifecycle.*
import com.example.dolladollabills.db.budget.Budget
import com.example.dolladollabills.db.budget.BudgetRepository
import com.example.dolladollabills.db.budget.BudgetViewModel
import java.lang.IllegalArgumentException

class GoalViewModel(private val repository: GoalRepository): ViewModel() {
    val allGoalsLiveData: LiveData<List<Goal>>  = repository.allGoals.asLiveData()

    fun insert(goal: Goal) {
        repository.insert(goal)
    }

    fun delete(id: Long) {
        repository.delete(id)
    }
}

class GoalViewModelFactory (private val repository: GoalRepository) :
    ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(GoalViewModel::class.java))
            return GoalViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}