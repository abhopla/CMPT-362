package com.example.dolladollabills.Analysis.spending

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dolladollabills.Analysis.AnalysisUtil.intializaLongList_Any
import com.example.dolladollabills.Analysis.AnalysisUtil.intializaSpendingGraphData
import com.example.dolladollabills.R
import com.example.dolladollabills.databinding.FragmentFebmonthlyspendingBinding
import com.example.dolladollabills.databinding.FragmentJanmonthlyspendingBinding
import com.example.dolladollabills.databinding.FragmentMonthlyspendingBinding
import com.example.dolladollabills.databinding.FragmentNovmonthlyspendingBinding
import com.example.dolladollabills.db.category.*
import com.example.dolladollabills.db.transaction.*
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

// TODO: without income , use current month

class NovSpendingFragment : Fragment() {
    private var _binding: FragmentNovmonthlyspendingBinding? = null
    private val binding get() = _binding!!

    private val log = "log-monthly saving"

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
    private val currentMonth = 11 //Nov
    private val incomeId = 11L


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNovmonthlyspendingBinding.inflate(inflater, container, false)
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
            }
        })

        transactionDatabase = TransactionDatabase.getInstance(requireActivity())
        trasactionDatabaseDao = transactionDatabase.transactionDatabaseDao
        transactionRepository = TransactionRepository(trasactionDatabaseDao)
        transactionViewModelFactory = TransactionViewModelFactory(transactionRepository)
        transactionViewModel = ViewModelProvider(requireActivity(), transactionViewModelFactory).get(TransactionViewModel::class.java)

        transactionViewModel.allTransactionsLiveData.observe(requireActivity(), Observer { it ->
//            Log.d(log,it.toString())
            var categorySize = categoryList.size

            //initialize
            var spendingList : MutableList<Double> = mutableListOf()
            for (n in 0..categorySize-1){
                spendingList.add(0.0)
            }
            for (transaction: Transaction in it) {
                val sdf = SimpleDateFormat("M")
                val month = sdf.format(transaction.milliseconds).toInt()
                if ((transaction.category_id != incomeId) && (Date(transaction.milliseconds).month+1 == currentMonth)) {
                    spendingList[transaction.category_id.toInt()] += transaction.amount / 100.0
                }
            }

            Log.d(log,"spendinglist"+spendingList.toString())
            Log.d(log,"categorylist"+categoryList.toString())

            var spendingListNotZeroCount = 0
            for (n in spendingList){
                if (n != 0.0){
                    spendingListNotZeroCount++
                }
            }
            
            var graphData = intializaSpendingGraphData(spendingListNotZeroCount)


            var graphDataIndex = 0
            for (n in 0..spendingList.size-1){
                if (spendingList[n] != 0.0){
                    var data = arrayOf(categoryList[n],spendingList[n])
                    graphData[graphDataIndex] = data
                    graphDataIndex++
                }
            }


            var arrayAny : Array<Any> = intializaLongList_Any(spendingListNotZeroCount)
            var i = 0
            for (n in graphData){
                arrayAny[i] = n
                i++
            }

            val aaChartView = binding.monthlySpendingChart
            val aaChartModel : AAChartModel = AAChartModel()
                .chartType(AAChartType.Pie)
                .dataLabelsEnabled(false)
                .series(arrayOf(
                    AASeriesElement()
                        .name("Spending")
                        .data(arrayAny)
                )
                )

            aaChartView.aa_drawChartWithChartModel(aaChartModel)
        })









        return view
    }

}


