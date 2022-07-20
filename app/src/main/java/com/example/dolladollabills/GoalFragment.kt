package com.example.dolladollabills

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.AdapterView
import android.widget.ArrayAdapter as ArrayAdapter1

private val GOALS = arrayOf(
    "Spend only twice at fast food places", "Limit spending on alcohol"
)
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var listView: ListView
private lateinit var view: View

/**
 * A simple [Fragment] subclass.
 * Use the [GraphFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoalFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


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


        val arrayAdapter: ArrayAdapter1<String> = ArrayAdapter1<String>(requireActivity(),
            android.R.layout.simple_list_item_1, GOALS)
        listView.adapter = arrayAdapter

        return view
    }

    fun onGoalSet(commentt: String) {
        val myDialog = Dialogue()
        val bundle = Bundle()
        bundle.putInt(Dialogue.DIALOG_KEY, Dialogue.COMMENT_DIALOG)

//                bundle.putString(Dialogue.TITLE_KEY, listentry)
        myDialog.arguments = bundle
        myDialog.show(requireActivity().supportFragmentManager, "my dialog")
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
}