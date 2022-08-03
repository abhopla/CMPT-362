package com.example.dolladollabills.db.goals

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.dolladollabills.R

class GoalListAdapter(private val context: Context, private var goalList: List<Goal>): BaseAdapter() {

    override fun getItem(position: Int): Any {
        return goalList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return goalList.size
    }

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {

        val view: View = View.inflate(context, R.layout.goal_list_adapter,null)
        val textViewgoal = view.findViewById(R.id.goaltext) as TextView
        print("debug: $goalList.get(position).name\n")
        textViewgoal.text = goalList.get(position).name

        return view
    }
    fun replace(newgoallist: List<Goal>){
        goalList = newgoallist
    }

}