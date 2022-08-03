package com.example.dolladollabills.Analysis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dolladollabills.R
import com.example.dolladollabills.databinding.FragmentMonthlyBinding
import com.example.dolladollabills.databinding.FragmentMonthlybudgetBinding
import com.example.dolladollabills.databinding.FragmentMonthlyspendingBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement


class SavingFragment : Fragment() {
    private var _binding: FragmentMonthlybudgetBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMonthlybudgetBinding.inflate(inflater, container, false)
        val view = binding.root

        val aaChartView = binding.monthlyBudgetChart
        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .dataLabelsEnabled(true)
            .series(arrayOf(
                AASeriesElement()
                    .name("Saving ")
                    .data(arrayOf(110,200,300,400)),
                AASeriesElement()
                    .name("Income")
                    .data(arrayOf(400,700,800,1000)),
                AASeriesElement()
                    .name("Spending")
                    .data(arrayOf(400,700,584,1000)),
            )
            )

        aaChartView.aa_drawChartWithChartModel(aaChartModel)






        return view
    }

}