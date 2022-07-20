package com.example.dolladollabills.db.transaction

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.dolladollabills.R
import com.example.dolladollabills.db.transaction.Transaction

class TransactionAdapter(private val context: Context, private var transactionList: List<Transaction>) : BaseAdapter(){

    override fun getItem(position: Int): Any {
        return transactionList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return transactionList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.adapter_transaction,null)
        return view
    }

    fun replace(newTransactionList: List<Transaction>){
        transactionList = newTransactionList
    }

}