package com.example.dolladollabills.Analysis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dolladollabills.Analysis.budgetSpending.MonthlyBudgetFragment
import com.example.dolladollabills.Analysis.spending.*
import com.example.dolladollabills.FragmentStateAdapter
import com.example.dolladollabills.R
import com.example.dolladollabills.databinding.FragmentAnalysisBinding
import com.example.dolladollabills.databinding.FragmentSpendingBinding
import com.google.android.material.tabs.TabLayoutMediator

class SpendingFragment : Fragment() {
    private var _binding: FragmentSpendingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentSpendingBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewPager3 = binding.viewPagerTest
        val tabLayout3 = binding.tabLayoutTest
        val tabTitles2 = arrayOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Year")
        var fragment3 = ArrayList<Fragment>()
        fragment3.add(JanSpendingFragment()); fragment3.add(FebSpendingFragment()); fragment3.add(MarSpendingFragment()); fragment3.add(AprSpendingFragment());
        fragment3.add(MaySpendingFragment()); fragment3.add(JunSpendingFragment()); fragment3.add(JulSpendingFragment()); fragment3.add(AugSpendingFragment());
        fragment3.add(SepSpendingFragment()); fragment3.add(OctSpendingFragment()); fragment3.add(NovSpendingFragment()); fragment3.add(DecSpendingFragment());
        fragment3.add(YearSpendingFragment());

        val myFragmentStateAdapter3 = FragmentStateAdapter(requireActivity(),fragment3)
        viewPager3.adapter = myFragmentStateAdapter3

        val tabConfigurationStrategy3 = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = tabTitles2[position]
        }
        val tabLayoutMediator3 = TabLayoutMediator(tabLayout3, viewPager3, tabConfigurationStrategy3)
        tabLayoutMediator3.attach()

        return view
    }


}