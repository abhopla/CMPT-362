package com.example.dolladollabills.db.category

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.dolladollabills.R
import com.example.dolladollabills.db.transaction.Transaction

class CategoryAdapter(private val context: Context, private var categoryList: List<Category>) : BaseAdapter(){

    override fun getItem(position: Int): Any {
        return categoryList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return categoryList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.adapter_transaction,null)
        return view
    }

    fun replace(newCategoryList: List<Category>){
        categoryList = newCategoryList
    }

}