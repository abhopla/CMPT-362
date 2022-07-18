package com.example.dolladollabills.db.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.count
import java.lang.IllegalArgumentException

class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {
    val allTransactionsLiveData: LiveData<List<Transaction>>
            = repository.allTransactions.asLiveData()

    fun insert(transaction: Transaction) {
        repository.insert(transaction)
    }

    fun delete(id: Long) {
        repository.delete(id)
    }

    suspend fun getCount() : Int {
        return repository.allTransactions.count()
    }
}

class TransactionViewModelFactory (private val repository: TransactionRepository) :
    ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(TransactionViewModel::class.java))
            return TransactionViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}