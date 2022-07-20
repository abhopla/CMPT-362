package com.example.dolladollabills

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dolladollabills.db.category.*
import com.example.dolladollabills.db.transaction.*
import kotlinx.coroutines.*
import java.util.*
import androidx.fragment.app.FragmentActivity



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private var dialog = DollaDialog()

private lateinit var transactionDatabase: TransactionDatabase
private lateinit var transactionDatabaseDao: TransactionDatabaseDao
private lateinit var transactionRepository: TransactionRepository
private lateinit var transactionViewModelFactory: TransactionViewModelFactory
private lateinit var transactionViewModel: TransactionViewModel

private lateinit var categoryDatabase: CategoryDatabase
private lateinit var categoryDatabaseDao: CategoryDatabaseDao
private lateinit var categoryRepository: CategoryRepository
private lateinit var categoryViewModelFactory: CategoryViewModelFactory
private lateinit var categoryViewModel: CategoryViewModel

private lateinit var descriptionEdit : EditText
private lateinit var typeSpin : Spinner
private lateinit var amountEdit: EditText

private var categoryList: MutableList<String> = mutableListOf()

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
        initializeDatabases()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_entry, container, false)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        finalizeViews(view)
        return view
    }

    private fun finalizeViews(view: View) {
        descriptionEdit = view.findViewById(R.id.entry_transaction_description_edit) as EditText
        typeSpin = view.findViewById(R.id.entry_transaction_type_spin) as Spinner

        categoryViewModel.allCategoriesLiveData.observe(requireActivity()) { categories ->
            categoryList = mutableListOf()
            for (category: Category in categories) {
                categoryList.add(category.name)
                Log.d("WHYYY", category.name)
            }
            val dataAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, categoryList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpin.adapter = dataAdapter
        }

        amountEdit = view.findViewById(R.id.entry_transaction_amount_edit) as EditText
        amountEdit.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        val saveButton: Button = view.findViewById(R.id.entry_save_button)
        saveButton.setOnClickListener() { onSaveClick(view) }

        val addCategoryButton: Button = view.findViewById(R.id.entry_add_category_button)
        addCategoryButton.setOnClickListener() { onAddCategoryClick(view) }
    }

    private fun initializeDatabases() {
        transactionDatabase = TransactionDatabase.getInstance(this.requireActivity())
        transactionDatabaseDao = transactionDatabase.transactionDatabaseDao
        transactionRepository = TransactionRepository(transactionDatabaseDao)
        transactionViewModelFactory = TransactionViewModelFactory(transactionRepository)
        transactionViewModel = ViewModelProvider(
            requireActivity(),
            transactionViewModelFactory
        )[TransactionViewModel::class.java]

        categoryDatabase = CategoryDatabase.getInstance(this.requireActivity())
        categoryDatabaseDao = categoryDatabase.categoryDatabaseDao
        categoryRepository = CategoryRepository(categoryDatabaseDao)
        categoryViewModelFactory = CategoryViewModelFactory(categoryRepository)
        categoryViewModel = ViewModelProvider(
            requireActivity(),
            categoryViewModelFactory
        )[CategoryViewModel::class.java]

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

    private fun onSaveClick(view: View) {
        val transaction = Transaction()
        transaction.amount = (amountEdit.text.toString().toDouble() * 100).toLong()
        transaction.description = descriptionEdit.text.toString()
        transaction.category_id = typeSpin.selectedItemPosition.toLong()
        transaction.milliseconds = Calendar.getInstance().timeInMillis

        transactionViewModel.insert(transaction)

        val toast = Toast.makeText(this.requireActivity(), "Transaction added", Toast.LENGTH_SHORT)
        toast.show()
    }


    private fun onAddCategoryClick(view: View) {
        val bundle = Bundle()
        bundle.putInt(DollaDialog.DIALOG_KEY, DollaDialog.ADD_CATEGORY_DIALOG)
        dialog.arguments = bundle
        dialog.show((activity as FragmentActivity).supportFragmentManager, "category dialog")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("WHYYY", "VIEWSTATERESTORED")
    }

}