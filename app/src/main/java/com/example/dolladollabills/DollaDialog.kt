package com.example.dolladollabills

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.dolladollabills.db.category.*
import com.example.dolladollabills.db.transaction.Transaction
import java.util.*

class DollaDialog : DialogFragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    var dialogValues: Bundle = Bundle()
    private var currentDialogID: Int = 0

    private lateinit var editText: EditText

    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var categoryDatabaseDao: CategoryDatabaseDao
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var categoryViewModelFactory: CategoryViewModelFactory
    private lateinit var categoryViewModel: CategoryViewModel
    private val calendar = Calendar.getInstance()

    companion object{
        const val DIALOG_KEY = "my_dialog_key"
        const val ADD_CATEGORY_DIALOG = 1
        const val TIME_DIALOG = 2
        const val DATE_DIALOG = 3
    }

    private val fieldKeys = listOf(
        "",
        "add_category_field",
        "time_field",
        "date_field"
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        val dialogId = bundle?.getInt(DIALOG_KEY)

        dialogValues = Bundle()
        if (dialogId != null) {
            currentDialogID = dialogId
        }

        initializeDatabase()

        when (dialogId) {
            ADD_CATEGORY_DIALOG -> return makeEditTextDialog(dialogId)
            TIME_DIALOG -> return makeTimeDialog()
        }

        return makeDateDialog()

    }

    private fun initializeDatabase() {
        categoryDatabase = CategoryDatabase.getInstance(this.requireActivity())
        categoryDatabaseDao = categoryDatabase.categoryDatabaseDao
        categoryRepository = CategoryRepository(categoryDatabaseDao)
        categoryViewModelFactory = CategoryViewModelFactory(categoryRepository)
        categoryViewModel = ViewModelProvider(
            requireActivity(),
            categoryViewModelFactory
        )[CategoryViewModel::class.java]
    }

    private fun makeEditTextDialog(dialogId: Int?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        var title = ""

        val view: View = requireActivity().layoutInflater.inflate(R.layout.edit_text_dialog, null)
        builder.setView(view)

        editText = view.findViewById<EditText>(R.id.edit_text_dialog_edit_text)

        when (dialogId) {
            ADD_CATEGORY_DIALOG -> {
                builder.setTitle("Add Category")
                builder.setPositiveButton("ok", addCategoryOnClick())
                editText.inputType = InputType.TYPE_CLASS_TEXT
            }
        }

        builder.setNegativeButton("cancel", null)
        return builder.create()
    }

    private fun makeTimeDialog(): Dialog {
        val timePickerDialog = TimePickerDialog(
            requireActivity(), this, calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE), false
        )
        timePickerDialog.show()
        return timePickerDialog
    }

    private fun makeDateDialog(): Dialog {
        val datePickerDialog = DatePickerDialog(
            requireActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
        return datePickerDialog
    }

    private fun addCategoryOnClick() =
        DialogInterface.OnClickListener() { _: DialogInterface, _: Int ->
            if (editText != null) {

                val category = Category()
                category.name = editText.text.toString()

                val handler = Handler();
                handler.postDelayed(Runnable() {
                    run() {
                        categoryViewModel.insert(category)
                    }
                }, 100);

            }
        }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        dialogValues.putInt("hour_field", hourOfDay)
        dialogValues.putInt("minute_field", minute)
    }

    override fun onDateSet(p0: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        dialogValues.putInt("year_field", year)
        dialogValues.putInt("month_field", monthOfYear)
        dialogValues.putInt("day_field", dayOfMonth)
    }
}