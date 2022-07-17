package com.example.dolladollabills

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dolladollabills.Analysis.MonthlyFragment
import com.example.dolladollabills.Analysis.QuaterlyFragment
import com.example.dolladollabills.Analysis.YearlyFragment
import com.example.dolladollabills.databinding.FragmentAnalysisBinding
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

        val viewPager = binding.viewPagerAnalysis
        val tabLayout = binding.tabLayoutAnalysis
        var fragment = ArrayList<Fragment>()
        fragment.add(MonthlyFragment()); fragment.add(QuaterlyFragment()); fragment.add(YearlyFragment());

        val myFragmentStateAdapter = FragmentStateAdapter(requireActivity(),fragment)
        viewPager.adapter = myFragmentStateAdapter

        val tabTitles = arrayOf("Monthly","Quaterly","Yearly")
        val tabConfigurationStrategy = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = tabTitles[position]
        }
        val tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager, tabConfigurationStrategy)
        tabLayoutMediator.attach()

        val viewPager2 = binding.viewPagerAnalysis2
        val tabLayout2 = binding.tabLayoutAnalysis2
        var fragment2 = ArrayList<Fragment>()
        fragment2.add(MonthlyFragment()); fragment2.add(QuaterlyFragment()); fragment2.add(YearlyFragment());

        val myFragmentStateAdapter2 = FragmentStateAdapter(requireActivity(),fragment2)
        viewPager2.adapter = myFragmentStateAdapter2

        val tabTitles2 = arrayOf("Monthly2","Quaterly2","Yearly2")
        val tabConfigurationStrategy2 = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = tabTitles2[position]
        }
        val tabLayoutMediator2 = TabLayoutMediator(tabLayout2, viewPager2, tabConfigurationStrategy2)
        tabLayoutMediator2.attach()


        return view
    }

}