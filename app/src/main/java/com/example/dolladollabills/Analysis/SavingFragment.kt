package com.example.dolladollabills.Analysis

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dolladollabills.Analysis.AnalysisUtil.intializaLongList
import com.example.dolladollabills.Analysis.AnalysisUtil.intializaLongList_Any
import com.example.dolladollabills.Analysis.AnalysisUtil.intializaMonth
import com.example.dolladollabills.R
import com.example.dolladollabills.databinding.FragmentMonthlybudgetBinding
import com.example.dolladollabills.databinding.FragmentMonthlyspendingBinding
import com.example.dolladollabills.db.category.*
import com.example.dolladollabills.db.transaction.*
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import java.time.LocalDateTime
import java.util.*

class SavingFragment : Fragment() {
    private var _binding: FragmentMonthlybudgetBinding? = null
    private val binding get() = _binding!!

    private val log = "log-saving fragment"

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
//    private val currentMonth = LocalDateTime.now().month.value //ex. AUGUST = 8
    private val currentMonth = 12 //ex. AUGUST = 8
    private var saving= intializaLongList(currentMonth)
    private var budget =  intializaLongList(currentMonth)
    private var spending =  intializaLongList(currentMonth)
    private var incomeId : Long = 11 // TODO: revise if we put income as dafult category
    private var monthCategory = intializaMonth(currentMonth)


    //milliseconds
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMonthlybudgetBinding.inflate(inflater, container, false)
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
                if (category.name == "Income"){
                    incomeId = category.id
                }
            }
        })

        transactionDatabase = TransactionDatabase.getInstance(requireActivity())
        trasactionDatabaseDao = transactionDatabase.transactionDatabaseDao
        transactionRepository = TransactionRepository(trasactionDatabaseDao)
        transactionViewModelFactory = TransactionViewModelFactory(transactionRepository)
        transactionViewModel = ViewModelProvider(requireActivity(), transactionViewModelFactory).get(TransactionViewModel::class.java)

        transactionViewModel.allTransactionsLiveData.observe(requireActivity(), Observer { it ->
            saving= intializaLongList(currentMonth)
            budget =  intializaLongList(currentMonth)
            spending =  intializaLongList(currentMonth)

            for (transaction: Transaction in it){
                if (transaction.category_id == incomeId){
                    //for Jan
                    if (Date(transaction.milliseconds).month+1 == 12){
                        budget[0] += transaction.amount //August > Date(transaction.milliseconds).month+1 > 7
                        continue
                    }
                    budget[Date(transaction.milliseconds).month+1] += transaction.amount //August > Date(transaction.milliseconds).month+1 > 7
                }else {
                    if (Date(transaction.milliseconds).month+1 == 12){
                        spending[0] += transaction.amount
                        continue
                    }
                    spending[Date(transaction.milliseconds).month+1] += transaction.amount

                }
            }

            for ( n in 0 until currentMonth){
                saving[n] = budget[n] - spending[n]
            }


            var savingData : Array<Any> = intializaLongList_Any(currentMonth)
            var budgetData : Array<Any> = intializaLongList_Any(currentMonth)
            var spendingData : Array<Any> = intializaLongList_Any(currentMonth)
            for (n in 0 until currentMonth){
                savingData[n] = saving[n]
                budgetData[n] = budget[n]
                spendingData[n] = spending[n]
            }

            val aaChartView = binding.monthlyBudgetChart
            val aaChartModel : AAChartModel = AAChartModel()
                .chartType(AAChartType.Line)
                .categories(monthCategory)
                .dataLabelsEnabled(true)
                .series(arrayOf(
                    AASeriesElement()
                        .name("Saving ")
                        .data(savingData),
                    AASeriesElement()
                        .name("Income")
                        .data(budgetData),
                    AASeriesElement()
                        .name("Spending")
                        .data(spendingData),
                )
                )
            aaChartView.aa_drawChartWithChartModel(aaChartModel)
        })
        return view
    }

}