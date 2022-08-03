package com.example.dolladollabills

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dolladollabills.Analysis.MonthlyFragment
import com.example.dolladollabills.Analysis.QuaterlyFragment
import com.example.dolladollabills.Analysis.SavingFragment
import com.example.dolladollabills.Analysis.YearlyFragment
import com.example.dolladollabills.Analysis.budgetSpending.MonthlyBudgetFragment
import com.example.dolladollabills.Analysis.budgetSpending.QuaterlyBudgetFragment
import com.example.dolladollabills.Analysis.budgetSpending.YearlyBudgetFragment
import com.example.dolladollabills.Analysis.spending.MonthlySpendingFragment
import com.example.dolladollabills.Analysis.spending.QuaterlySpendingFragment
import com.example.dolladollabills.Analysis.spending.YearlySpendingFragment
import com.example.dolladollabills.databinding.FragmentAnalysisBinding
import com.example.dolladollabills.db.budget.BudgetDatabase
import com.example.dolladollabills.db.budget.BudgetRepository
import com.example.dolladollabills.db.category.CategoryDatabase
import com.example.dolladollabills.db.transaction.TransactionDatabase
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.google.android.material.tabs.TabLayoutMediator

class AnalysisFragment : Fragment() {
    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentAnalysisBinding.inflate(inflater, container, false)
        val view = binding.root

        //first graph
        val viewPager = binding.viewPagerAnalysis
        val tabLayout = binding.tabLayoutAnalysis
        var fragment = ArrayList<Fragment>()
        fragment.add(MonthlySpendingFragment()); fragment.add(QuaterlySpendingFragment()); fragment.add(YearlySpendingFragment());

        val myFragmentStateAdapter = FragmentStateAdapter(requireActivity(),fragment)
        viewPager.adapter = myFragmentStateAdapter

        val tabTitles = arrayOf("Monthly","Quaterly","Yearly")
        val tabConfigurationStrategy = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = tabTitles[position]
        }
        val tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager, tabConfigurationStrategy)
        tabLayoutMediator.attach()

        //second graph
        val viewPager2 = binding.viewPagerAnalysis2
        val tabLayout2 = binding.tabLayoutAnalysis2
        var fragment2 = ArrayList<Fragment>()
        fragment2.add(MonthlyBudgetFragment()); fragment2.add(QuaterlyBudgetFragment()); fragment2.add(
            YearlyBudgetFragment()
        );

        val myFragmentStateAdapter2 = FragmentStateAdapter(requireActivity(),fragment2)
        viewPager2.adapter = myFragmentStateAdapter2

        val tabTitles2 = arrayOf("Monthly","Quaterly","Yearly")
        val tabConfigurationStrategy2 = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = tabTitles2[position]
        }
        val tabLayoutMediator2 = TabLayoutMediator(tabLayout2, viewPager2, tabConfigurationStrategy2)
        tabLayoutMediator2.attach()

        //third graph
        val viewPager3 = binding.viewPagerAnalysis3
        val tabLayout3 = binding.tabLayoutAnalysis3
        var fragment3 = ArrayList<Fragment>()
        fragment3.add(SavingFragment());

        val myFragmentStateAdapter3 = FragmentStateAdapter(requireActivity(),fragment3)
        viewPager3.adapter = myFragmentStateAdapter3

        val tabTitles3 = arrayOf(" ")
        val tabConfigurationStrategy3 = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = tabTitles2[position]
        }
        val tabLayoutMediator3 = TabLayoutMediator(tabLayout3, viewPager3, tabConfigurationStrategy3)
        tabLayoutMediator3.attach()


        return view
    }

}