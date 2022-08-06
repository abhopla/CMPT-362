package com.example.dolladollabills.Analysis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dolladollabills.Analysis.budgetSpending.*
import com.example.dolladollabills.Analysis.spending.*
import com.example.dolladollabills.FragmentStateAdapter
import com.example.dolladollabills.R
import com.example.dolladollabills.databinding.FragmentAnalysisBinding
import com.example.dolladollabills.databinding.FragmentBudgetBinding
import com.example.dolladollabills.databinding.FragmentSpendingBinding
import com.google.android.material.tabs.TabLayoutMediator

class BudgetFragment : Fragment() {
    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentBudgetBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewPager3 = binding.viewPagerTest
        val tabLayout3 = binding.tabLayoutTest
        val tabTitles2 = arrayOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Year")
        var fragment3 = ArrayList<Fragment>()
        fragment3.add(JanBudgetFragment()); fragment3.add(FebBudgetFragment()); fragment3.add(MarBudgetFragment()); fragment3.add(AprBudgetFragment());
        fragment3.add(MayBudgetFragment()); fragment3.add(JunBudgetFragment()); fragment3.add(JulBudgetFragment()); fragment3.add(AugBudgetFragment());
        fragment3.add(SepBudgetFragment()); fragment3.add(OctBudgetFragment()); fragment3.add(NovBudgetFragment()); fragment3.add(DecBudgetFragment());
        fragment3.add(YearBudgetFragment());

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