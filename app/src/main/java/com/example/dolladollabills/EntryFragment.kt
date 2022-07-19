package com.example.dolladollabills

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dolladollabills.db.transaction.*
import kotlinx.coroutines.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var database: TransactionDatabase
private lateinit var databaseDao: TransactionDatabaseDao
private lateinit var repository: TransactionRepository
private lateinit var viewModelFactory: TransactionViewModelFactory
private lateinit var transactionViewModel: TransactionViewModel

private lateinit var descriptionEdit : EditText
private lateinit var typeEdit : EditText
private lateinit var amountEdit: EditText

/**
 * A simple [Fragment] subclass.
 * Use the [EntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EntryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        initializeDatabase()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_entry, container, false)
        finalizeViews(view)
        return view
    }

    private fun finalizeViews(view: View) {
        descriptionEdit = view.findViewById(R.id.entry_transaction_description_edit) as EditText
        typeEdit = view.findViewById(R.id.entry_transaction_type_edit) as EditText
        amountEdit = view.findViewById(R.id.entry_transaction_amount_edit) as EditText

        amountEdit.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        val saveButton: Button = view.findViewById(R.id.entry_save_button)
        saveButton.setOnClickListener() { onSaveClick(view) }

        val cancelButton: Button = view.findViewById(R.id.entry_cancel_button)
        cancelButton.setOnClickListener() { onCancelClick(view) }

    }


    private fun initializeDatabase() {
        database = TransactionDatabase.getInstance(this.requireActivity())
        databaseDao = database.transactionDatabaseDao
        repository = TransactionRepository(databaseDao)
        viewModelFactory = TransactionViewModelFactory(repository)
        transactionViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[TransactionViewModel::class.java]
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EntryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EntryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun onSaveClick(view: View) {
        val transaction = Transaction()
        transaction.amount = (amountEdit.text.toString().toDouble() * 100).toLong()
        transaction.description = descriptionEdit.text.toString()
        transaction.category_id = 5L
        transaction.milliseconds = Calendar.getInstance().timeInMillis

        transactionViewModel.insert(transaction)

        val toast = Toast.makeText(this.requireActivity(), "Transaction added", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onCancelClick(view: View){
        requireActivity().finish()
    }

}