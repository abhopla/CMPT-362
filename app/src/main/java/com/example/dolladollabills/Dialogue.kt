package com.example.dolladollabills


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.dolladollabills.db.goals.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.ArrayList


private lateinit var editTextt: EditText

class Dialogue : DialogFragment(), DialogInterface.OnClickListener {

    private var title: String? = null
    private lateinit var goalViewmodel: GoalViewModel
    private lateinit var goalListAdapter: GoalListAdapter
    private lateinit var database: GoalDatabase
    private lateinit var databaseDao: GoalDatabaseDao
    private lateinit var repository: GoalRepository
    private lateinit var viewModelFactory: GoalViewModelFactory
    private lateinit var arrayList: ArrayList<Goal>

    //Necessary keys to determine the correct Dialog
    companion object {
        const val TAG = "myDialog"
        const val DIALOG_KEY = "dialog"
        const val COMMENT_DIALOG = 2
        const val DURATION_DIALOG = 3
        const val TITLE_KEY = "EnterData"

    }

    //Creates a dialog based on the input
    @OptIn(InternalCoroutinesApi::class)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        lateinit var ret: Dialog
        arrayList = ArrayList()
        goalListAdapter = GoalListAdapter(requireActivity(), arrayList)


        database = GoalDatabase.getInstance(requireActivity())
        databaseDao = database.goalDatabaseDao
        repository = GoalRepository(databaseDao)
        viewModelFactory = GoalViewModelFactory(repository)
        goalViewmodel = ViewModelProvider(requireActivity(), viewModelFactory).get(GoalViewModel::class.java)

        val bundle = arguments
        val dialogId = bundle?.getInt(DIALOG_KEY)

        val builder = AlertDialog.Builder(requireActivity())
        val view: View = requireActivity().layoutInflater.inflate(R.layout.goal_dialogue, null)
        editTextt = view.findViewById(R.id.edit_goal)
        builder.setView(view)
        builder.setTitle("Enter Your Budget Goal")
//        val parentgoal: GoalFragment =
//            parentFragment as GoalFragment
        builder.setPositiveButton("ok") { dialog, which ->
            if (editTextt.text != null) {
                val tesstt = editTextt.text.toString()
                onGoalSet(tesstt)

            } else {
                onGoalSet(" ")
            }

        }
        builder.setNegativeButton("cancel") { dialog, which ->

            onGoalSet(" ")
        }
        ret = builder.create()
        return ret
    }


        override fun onClick(dialog: DialogInterface, item: Int) {
            if (item == DialogInterface.BUTTON_POSITIVE) {
                Toast.makeText(activity, "ok clicked", Toast.LENGTH_LONG).show()
            } else if (item == DialogInterface.BUTTON_NEGATIVE) {
                Toast.makeText(activity, "cancel clicked", Toast.LENGTH_LONG).show()
            }
        }

    fun onGoalSet( goall: String) {

        goal.name = goall
        println("debug dbb: ${goal.name}")
        goalListAdapter.replace(arrayList)
        goalListAdapter.notifyDataSetChanged()
        goalViewmodel.insert(goal)


    }
}