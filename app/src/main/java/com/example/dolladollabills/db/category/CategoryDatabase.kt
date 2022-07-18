package com.example.dolladollabills.db.category

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Category::class], version = 1)
abstract class CategoryDatabase : RoomDatabase() {
    abstract val transactionDatabaseDao: CategoryDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: CategoryDatabase? = null

        fun getInstance(context: Context) : CategoryDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, CategoryDatabase::class.java, "category_table").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}