package com.example.dolladollabills.Analysis.budgetSpending

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dolladollabills.R
import com.example.dolladollabills.databinding.*
import com.example.dolladollabills.db.budget.*
import com.example.dolladollabills.db.category.*
import com.example.dolladollabills.db.transaction.*
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import java.time.LocalDateTime
import java.util.*


class MayBudgetFragment : Fragment() {
    private var _binding: FragmentMaybudgetBinding? = null
    private val binding get() = _binding!!

    private val log = "log-monthly budget"

    private lateinit var transactionDatabase: TransactionDatabase
    private lateinit var trasactionDatabaseDao: TransactionDatabaseDao
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var transactionViewModelFactory: TransactionViewModelFactory
    private lateinit var transactionViewModel: TransactionViewModel

    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var categoryDatabaseDao: CategoryDatabaseDao
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var categoryViewModelFactory: CategoryViewModelFactory
    private lateinit var categoryViewModel: CategoryViewModel

    private var categoryList : MutableList<String> = mutableListOf()
//    private val currentMonth = LocalDateTime.now().month.value //ex. AUGUST = 8 >
    private val currentMonth = 5 //May

    private var budget : Double = 0.0
    private var spending : Double = 0.0
    private var incomeId : Long = 11

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMaybudgetBinding.inflate(inflater, container, false)
        val view = binding.root

        categoryDatabase = CategoryDatabase.getInstance(requireActivity())
        categoryDatabaseDao = categoryDatabase.categoryDatabaseDao
        categoryRepository = CategoryRepository(categoryDatabaseDao)
        categoryViewModelFactory = CategoryViewModelFactory(categoryRepository)
        categoryViewModel = ViewModelProvider(requireActivity(), categoryViewModelFactory).get(CategoryViewModel::class.java)

        //id start from 0
        categoryViewModel.allCategoriesLiveData.observe(requireActivity(), Observer { it ->
            for (category :Category in it){
                categoryList.add(category.name)
                if (category.name == "income"){
                    incomeId = category.id
                }
            }
        })

        transactionDatabase = TransactionDatabase.getInstance(requireActivity())
        trasactionDatabaseDao = transactionDatabase.transactionDatabaseDao
        transactionRepository = TransactionRepository(trasactionDatabaseDao)
        transactionViewModelFactory = TransactionViewModelFactory(transactionRepository)
        transactionViewModel = ViewModelProvider(requireActivity(), transactionViewModelFactory).get(TransactionViewModel::class.java)
        var data = transactionViewModel.allTransactionsLiveData

        transactionViewModel.allTransactionsLiveData.observe(requireActivity(), Observer { it ->
            Log.d(log,it.toString())
            budget = 0.0
            spending = 0.0

            //ex Date(transaction.milliseconds).month > August : 6
            //Date(transaction.milliseconds).month+ 2 August : 8

            for (transaction: Transaction in it){
                if (Date(transaction.milliseconds).month+1 == currentMonth){
                    if (transaction.category_id == incomeId ){
                        budget += transaction.amount / 100.0
                    }else{
                        spending += transaction.amount / 100.0
                    }
                }

            }
            budget = (budget * 100).toLong() / 100.0
            spending = (spending * 100).toLong() / 100.0

            val aaChartView = binding.monthlyBudgetChart
            val aaChartModel : AAChartModel = AAChartModel()
                .chartType(AAChartType.Bar)
                .dataLabelsEnabled(true)
                .series(arrayOf(
                    AASeriesElement()
                        .name("Budget")
                        .data(arrayOf(budget)),
                    AASeriesElement()
                        .name("Spending")
                        .data(arrayOf(spending)),
                )
                )

            aaChartView.aa_drawChartWithChartModel(aaChartModel)


        })

        return view
    }

}