package com.example.dolladollabills.db.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDatabaseDao {

    @Insert
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM category_table")
    fun getAllCategories(): Flow<List<Category>>

    @Query("DELETE FROM category_table WHERE id = :key")
    suspend fun deleteCategory(key: Long)
}