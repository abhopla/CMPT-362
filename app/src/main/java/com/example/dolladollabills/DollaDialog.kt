package com.example.dolladollabills

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.dolladollabills.db.category.*
import com.example.dolladollabills.db.transaction.Transaction
import java.util.*

class DollaDialog : DialogFragment() {

    var dialogValues: Bundle = Bundle()
    private var currentDialogID: Int = 0

    private lateinit var editText: EditText

    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var categoryDatabaseDao: CategoryDatabaseDao
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var categoryViewModelFactory: CategoryViewModelFactory
    private lateinit var categoryViewModel: CategoryViewModel

    companion object{
        const val DIALOG_KEY = "my_dialog_key"
        const val ADD_CATEGORY_DIALOG = 1
    }

    private val fieldKeys = listOf(
        "",
        "add_category_field",
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        val dialogId = bundle?.getInt(DIALOG_KEY)

        dialogValues = Bundle()
        if (dialogId != null) {
            currentDialogID = dialogId
        }

        initializeDatabase()

        return makeEditTextDialog(dialogId)
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
}