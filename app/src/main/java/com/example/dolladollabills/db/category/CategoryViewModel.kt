package com.example.dolladollabills.db.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import java.lang.IllegalArgumentException

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {
    val allCategoriesLiveData: LiveData<List<Category>>
            = repository.allCategories.asLiveData()

    fun insert(category: Category) {
        repository.insert(category)
    }

    fun delete(id: Long) {
        repository.delete(id)
    }
}

class CategoryViewModelFactory (private val repository: CategoryRepository) :
    ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(CategoryViewModel::class.java))
            return CategoryViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}