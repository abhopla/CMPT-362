package com.example.dolladollabills.Analysis.budgetSpending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dolladollabills.R
import com.example.dolladollabills.databinding.ActivityMainBinding.inflate
import com.example.dolladollabills.databinding.FragmentMonthlyBinding
import com.example.dolladollabills.databinding.FragmentMonthlybudgetBinding
import com.example.dolladollabills.databinding.FragmentMonthlyspendingBinding
import com.example.dolladollabills.databinding.FragmentQuaterlybudgetBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement


class QuaterlyBudgetFragment : Fragment() {
    private var _binding: FragmentQuaterlybudgetBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuaterlybudgetBinding.inflate(inflater, container, false)

        val view = binding.root

        val aaChartView = binding.quaterlyBudgetChart
        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Bar)
            .dataLabelsEnabled(true)
            .series(arrayOf(
                AASeriesElement()
                    .name("Budget")
                    .data(arrayOf(110)),
                AASeriesElement()
                    .name("Spending")
                    .data(arrayOf(400)),
            )
            )

        aaChartView.aa_drawChartWithChartModel(aaChartModel)






        return view
    }

}