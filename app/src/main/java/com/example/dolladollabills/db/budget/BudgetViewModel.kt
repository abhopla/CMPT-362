package com.example.dolladollabills.db.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import java.lang.IllegalArgumentException

class BudgetViewModel(private val repository: BudgetRepository) : ViewModel() {
    val allCategoriesLiveData: LiveData<List<Budget>>
            = repository.allCategories.asLiveData()

    fun insert(category: Budget) {
        repository.insert(category)
    }

    fun delete(id: Long) {
        repository.delete(id)
    }
}

class BudgetViewModelFactory (private val repository: BudgetRepository) :
    ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(BudgetViewModel::class.java))
            return BudgetViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}