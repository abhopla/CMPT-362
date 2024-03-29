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
import androidx.appcompat.app.AppCompatActivity
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
private var categoryMap: MutableMap<String, Long> = mutableMapOf()

private var hour = -1
private var minute = -1
private var year = -1
private var month = -1
private var date = -1
private var spinnerPosition = 0


/**
 * A simple [Fragment] subclass.
 * Use the [EntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ManualTransactionActivity : AppCompatActivity() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_transaction)
        initializeDatabases()
        restoreSpinner(savedInstanceState)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        finalizeViews()

    }

    private fun restoreSpinner(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            spinnerPosition = savedInstanceState.getInt("spinner_value", spinnerPosition)
            if (typeSpin != null) {
                typeSpin.setSelection(spinnerPosition)
                typeSpin.prompt
            }
        }
    }

    private fun finalizeViews() {
        descriptionEdit = findViewById(R.id.entry_transaction_description_edit)
        typeSpin = findViewById(R.id.entry_transaction_type_spin)


        categoryViewModel.allCategoriesLiveData.observe(this) { categories ->
            categoryList = mutableListOf()
            categoryMap = mutableMapOf()
            for (category: Category in categories) {
                categoryList.add(category.name)
                categoryMap.put(category.name, category.id)
            }
            val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpin.adapter = dataAdapter
        }

        amountEdit = findViewById(R.id.entry_transaction_amount_edit)
        amountEdit.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        val dateButton: Button = findViewById(R.id.entry_date_button)
        dateButton.setOnClickListener() { onDateClick() }

        val timeButton: Button = findViewById(R.id.entry_time_button)
        timeButton.setOnClickListener() { onTimeClick() }

        val saveButton: Button = findViewById(R.id.entry_save_button)
        saveButton.setOnClickListener() { onSaveClick() }

        val addCategoryButton: Button = findViewById(R.id.entry_add_category_button)
        addCategoryButton.setOnClickListener() { onAddCategoryClick() }

        val backButton: Button = findViewById(R.id.entry_back_button)
        backButton.setOnClickListener() { finish() }
    }

    private fun initializeDatabases() {
        transactionDatabase = TransactionDatabase.getInstance(this)
        transactionDatabaseDao = transactionDatabase.transactionDatabaseDao
        transactionRepository = TransactionRepository(transactionDatabaseDao)
        transactionViewModelFactory = TransactionViewModelFactory(transactionRepository)
        transactionViewModel = ViewModelProvider(
            this,
            transactionViewModelFactory
        )[TransactionViewModel::class.java]

        categoryDatabase = CategoryDatabase.getInstance(this)
        categoryDatabaseDao = categoryDatabase.categoryDatabaseDao
        categoryRepository = CategoryRepository(categoryDatabaseDao)
        categoryViewModelFactory = CategoryViewModelFactory(categoryRepository)
        categoryViewModel = ViewModelProvider(
            this,
            categoryViewModelFactory
        )[CategoryViewModel::class.java]

    }

    private fun refreshDialogValues() {
        var dialogData = dialog.dialogValues
        hour = dialogData.getInt("hour_field", hour)
        minute = dialogData.getInt("minute_field", minute)
        year = dialogData.getInt("year_field", year)
        month = dialogData.getInt("month_field", month)
        date = dialogData.getInt("day_field", date)
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

    private fun onTimeClick() {
        refreshDialogValues()
        val bundle = Bundle()
        bundle.putInt(DollaDialog.DIALOG_KEY, DollaDialog.TIME_DIALOG)
        dialog.arguments = bundle
        dialog.show((this as FragmentActivity).supportFragmentManager, "category dialog")
    }

    private fun onDateClick() {
        refreshDialogValues()
        val bundle = Bundle()
        bundle.putInt(DollaDialog.DIALOG_KEY, DollaDialog.DATE_DIALOG)
        dialog.arguments = bundle
        dialog.show((this as FragmentActivity).supportFragmentManager, "category dialog")
    }

    private fun onSaveClick() {
        refreshDialogValues()

        try {
            val description = descriptionEdit.text.toString()
            val amount = amountEdit.text.toString().toDouble()
            val category = typeSpin.selectedItem.toString()
            var milliseconds = 0L
            val sdf = SimpleDateFormat("HH:mm MM dd yyyy")

            if (hour == -1) {
                if (year == -1) {
                    milliseconds = Calendar.getInstance().timeInMillis
                } else {
                    var curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
                    var curMinute = Calendar.getInstance().get(Calendar.MINUTE).toString()
                    var actualMonth = month + 1

                    val timeString = "$curHour:$curMinute $actualMonth $date $year"
                    milliseconds = sdf.parse(timeString).time
                }
            } else if (year == -1) {
                year = Calendar.getInstance().get(Calendar.YEAR)
                month = (Calendar.getInstance().get(Calendar.MONTH) + 1)
                date = Calendar.getInstance().get(Calendar.DATE)

                val timeString = "$hour:$minute $month $date $year"
                milliseconds = sdf.parse(timeString).time
            } else {
                val timeString = "$hour:$minute $month $date $year"
                milliseconds = sdf.parse(timeString).time
            }

            addTransaction(category, milliseconds, amount, description)
            addToast("Transaction added")
        } catch(e: Exception) {
            addToast("Error: Make sure all fields are filled before adding a transaction")
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
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }


    private fun onAddCategoryClick() {
        val bundle = Bundle()
        bundle.putInt(DollaDialog.DIALOG_KEY, DollaDialog.ADD_CATEGORY_DIALOG)
        dialog.arguments = bundle
        dialog.show((this as FragmentActivity).supportFragmentManager, "category dialog")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        refreshDialogValues()
        savedInstanceState.putInt("hour_value", hour)
        savedInstanceState.putInt("minute_value", minute)
        savedInstanceState.putInt("year_value", year)
        savedInstanceState.putInt("month_value", month)
        savedInstanceState.putInt("date_value", date)
        savedInstanceState.putInt("spinner_value", typeSpin.selectedItemPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            hour = savedInstanceState.getInt("hour_value", hour)
            minute = savedInstanceState.getInt("minute_value", minute)
            year = savedInstanceState.getInt("year_value", year)
            month = savedInstanceState.getInt("month_value", month)
            date = savedInstanceState.getInt("date_value", date)
        }
    }
}