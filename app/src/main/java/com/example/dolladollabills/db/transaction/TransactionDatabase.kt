package com.example.dolladollabills.db.transaction

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Transaction::class], version = 1)
abstract class TransactionDatabase : RoomDatabase() {
    abstract val transactionDatabaseDao: TransactionDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: TransactionDatabase? = null

        fun getInstance(context: Context) : TransactionDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, TransactionDatabase::class.java, "transaction_table").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}