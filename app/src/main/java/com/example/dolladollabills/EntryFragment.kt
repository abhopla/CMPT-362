package com.example.dolladollabills

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dolladollabills.db.category.*
import com.example.dolladollabills.db.transaction.*
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlin.math.abs

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

private lateinit var typeSpin : Spinner

private var categoryList: MutableList<String> = mutableListOf()
private var categoryMap: MutableMap<String, Long> = mutableMapOf()

private lateinit var resultLauncher: ActivityResultLauncher<Intent>


/**
 * A simple [Fragment] subclass.
 * Use the [EntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EntryFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        initializeDatabases()

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data?.data != null) {
                    addCSVData(result.data!!.data!!)
                }
            }
        }
    }

    private fun addCSVData(uri: Uri) {
        var errorsFound = false
        requireContext().contentResolver.openInputStream(uri)?.let {
            csvReader().open(it) {
                readAllAsSequence().forEach { row ->
                    if (row.size == 4 && row[0] != "Category") {
                        try {
                            val category = row[0]
                            var milliseconds = row[1].toLong()
                            val amount = row[2].toDouble()
                            val description = row[3]
                            addTransaction(category, milliseconds, amount, description)
                        } catch (e: Exception) {
                            errorsFound = true
                        }
                    } else if (row.size == 8 && row[0] != "Category") {
                        try {
                            val sdf = SimpleDateFormat("HH:mm MM dd yyyy")
                            val category = row[0]
                            val year = row[1].toInt()
                            val month = row[2].toInt()
                            val date = row[3].toInt()
                            val hour = row[4].toInt()
                            val minute = row[5].toInt()
                            val timeString = "$hour:$minute $month $date $year"
                            val milliseconds = sdf.parse(timeString).time
                            val amount = row[6].toDouble()
                            val description = row[7]
                            addTransaction(category, milliseconds, amount, description)
                        } catch (e: Exception) {
                            errorsFound = true
                        }
                    }
                }
            }
        }

        if (errorsFound) {
            addToast("Added transactions, but errors in the CSV were found for some rows")
        } else {
            addToast("Added transactions")
        }

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

        categoryViewModel.allCategoriesLiveData.observe(requireActivity()) { categories ->
            categoryList = mutableListOf()
            categoryMap = mutableMapOf()
            for (category: Category in categories) {
                categoryList.add(category.name)
                categoryMap.put(category.name, category.id)
            }
        }

        val csvImportButton: Button = view.findViewById(R.id.entry_csv_import_button)
        csvImportButton.setOnClickListener() { onCSVImportClick(view) }

        val manualAddTransaction: Button = view.findViewById(R.id.manual_add_transaction)
        manualAddTransaction.setOnClickListener() {onManualAddTransactionClick(view)}
    }

    private fun onManualAddTransactionClick(view: View) {
        val intent = Intent(activity, ManualTransactionActivity::class.java)
        startActivity(intent)
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

                }
            }
    }

    private fun addTransaction(categoryName: String, milliseconds: Long, amount: Double, description: String) {
        val transaction = Transaction()

        val text = abs(amount).toString()
        val integerPlaces = text.indexOf('.')
        val decimalPlaces = text.length - integerPlaces - 1

        if (decimalPlaces > 2) {
            addToast("Error: Too many decimal places in amount")
            return
        }

        if (categoryMap[categoryName] == null) {
            val category = Category()
            category.name = categoryName
            categoryViewModel.insert(category)
            Log.d("WHYYY", "Adding category")
            Log.d("WHYYY", "category: $categoryName\n")
        }

        val handler = Handler()
        handler.postDelayed( {
            transaction.amount = (amount * 100).toLong()
            transaction.description = description
            transaction.category_id = categoryMap.get(categoryName)!!
            transaction.milliseconds = milliseconds

            val categoryID = transaction.category_id
            Log.d("WHYYY", "Adding transaction")
            Log.d("WHYYY", "amount: $amount")
            Log.d("WHYYY", "description: $description")
            Log.d("WHYYY", "category: $categoryName")
            Log.d("WHYYY", "category_id: $categoryID")
            Log.d("WHYYY", "ms $milliseconds\n")

            transactionViewModel.insert(transaction)
        }, 100)

    }

    private fun addToast(message: String) {
        val toast = Toast.makeText(this.requireActivity(), message, Toast.LENGTH_SHORT)
        toast.show()
    }


    private fun onCSVImportClick(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/*"
        }
        resultLauncher.launch(intent)
    }

}