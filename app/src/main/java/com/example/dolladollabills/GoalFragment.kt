package com.example.dolladollabills


import android.content.Context

import android.content.Intent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dolladollabills.db.goals.*

//private var goalList = ArrayList<String>()




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var arrayList: ArrayList<Goal>

private lateinit var listView: ListView
private lateinit var view: View
private lateinit var goalbutton: Button
private lateinit var goalViewmodel: GoalViewModel
private lateinit var goalListAdapter: GoalListAdapter
private lateinit var database: GoalDatabase
private lateinit var databaseDao: GoalDatabaseDao
private lateinit var repository: GoalRepository
private lateinit var viewModelFactory: GoalViewModelFactory

val goal = Goal()


/**
 * A simple [Fragment] subclass.
 * Use the [GraphFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoalFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var share: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_goals, container, false)
        listView = view.findViewById(R.id.goallist)
        goalbutton =  view.findViewById(R.id.newgoal)
//
        //goalList.add( "Create a budget and save $500 a month")
//        goalList.add("Limit spending on alcohol")
        arrayList = ArrayList()
        goalListAdapter = GoalListAdapter(requireActivity(), arrayList)

        listView.adapter = goalListAdapter
        database = GoalDatabase.getInstance(requireActivity())
        databaseDao = database.goalDatabaseDao
        repository = GoalRepository(databaseDao)
        viewModelFactory = GoalViewModelFactory(repository)
        goalViewmodel = ViewModelProvider(requireActivity(), viewModelFactory).get(GoalViewModel::class.java)

        goalViewmodel.allGoalsLiveData.observe(requireActivity(), Observer { it ->

            goalListAdapter.replace(it)
            print("debug: it  ${it}")
            goalListAdapter.notifyDataSetChanged()

        })


        goalbutton.setOnClickListener{
            val myDialog = Dialogue()
            val bundle = Bundle()
            bundle.putInt(Dialogue.DIALOG_KEY, Dialogue.COMMENT_DIALOG)


            myDialog.arguments = bundle
            myDialog.show(requireActivity().supportFragmentManager, "my dialog")
        }



        share = view!!.findViewById(R.id.shareButton)
        share.setOnClickListener(){
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"New goal: Create a budget and save $500 a month")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share To:"))
        }

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GraphFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GoalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }



    }



    override fun onResume() {
        super.onResume()
        goalListAdapter.notifyDataSetChanged()
        listView.adapter = goalListAdapter



    }


}