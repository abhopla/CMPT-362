package com.example.dolladollabills.db.budget

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.dolladollabills.R
import com.example.dolladollabills.db.transaction.Transaction

class BudgetAdapter(private val context: Context, private var budgetList: List<Budget>) : BaseAdapter(){

    override fun getItem(position: Int): Any {
        return budgetList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return budgetList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.adapter_transaction,null)
        return view
    }

    fun replace(newBudgetList: List<Budget>){
        budgetList = newBudgetList
    }

}