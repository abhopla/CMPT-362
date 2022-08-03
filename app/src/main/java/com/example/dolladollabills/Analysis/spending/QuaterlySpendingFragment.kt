package com.example.dolladollabills.Analysis.spending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dolladollabills.R
import com.example.dolladollabills.databinding.FragmentMonthlyBinding
import com.example.dolladollabills.databinding.FragmentMonthlyspendingBinding
import com.example.dolladollabills.databinding.FragmentQuaterlyspendingBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement


class QuaterlySpendingFragment : Fragment() {
    private var _binding: FragmentQuaterlyspendingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuaterlyspendingBinding.inflate(inflater, container, false)
        val view = binding.root

        val aaChartView = binding.quaterlySpendingChart
        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Pie)
            .dataLabelsEnabled(false)
            .series(arrayOf(
                AASeriesElement()
                    .name("Food")
                    .data(arrayOf(110,50,20,40))
            )
            )

        aaChartView.aa_drawChartWithChartModel(aaChartModel)






        return view
    }

}