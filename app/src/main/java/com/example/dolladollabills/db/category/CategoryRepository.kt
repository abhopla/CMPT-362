package com.example.dolladollabills.db.category

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CategoryRepository(private val categoryDatabaseDao: CategoryDatabaseDao) {
    val allCategories : Flow<List<Category>> =
        categoryDatabaseDao.getAllCategories()

    fun insert(category: Category){
        CoroutineScope(Dispatchers.IO).launch{
            categoryDatabaseDao.insertCategory(category)
        }
    }

    fun delete(id: Long){
        CoroutineScope(Dispatchers.IO).launch {
            categoryDatabaseDao.deleteCategory(id)
        }
    }
}