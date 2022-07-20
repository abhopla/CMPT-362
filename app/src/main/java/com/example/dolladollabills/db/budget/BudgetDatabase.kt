package com.example.dolladollabills.db.budget

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Budget::class], version = 1)
abstract class BudgetDatabase : RoomDatabase() {
    abstract val budgetDatabaseDao: BudgetDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: BudgetDatabase? = null

        fun getInstance(context: Context) : BudgetDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, BudgetDatabase::class.java, "budget_table").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}