package com.example.dolladollabills.db.transaction

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TransactionRepository(private val transactionDatabaseDao: TransactionDatabaseDao) {
    val allTransactions : Flow<List<Transaction>> =
        transactionDatabaseDao.getAllTransactions()

    fun insert(transaction: Transaction){
        CoroutineScope(Dispatchers.IO).launch{
            transactionDatabaseDao.insertTransaction(transaction)
        }
    }

    fun delete(id: Long){
        CoroutineScope(Dispatchers.IO).launch {
            transactionDatabaseDao.deleteTransaction(id)
        }
    }
}