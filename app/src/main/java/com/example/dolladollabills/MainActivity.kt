package com.example.dolladollabills

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var entryFragment: EntryFragment
    private lateinit var analysisFragment: AnalysisFragment
    private lateinit var graphFragment: GraphFragment
    private lateinit var extraFragment: ExtraFragment
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var fragmentStateAdapter: FragmentStateAdapter
    private lateinit var fragments: ArrayList<Fragment>
    private val tabTitles = arrayOf("Entry", "Analysis", "Goals", "Extra") //Tab titles
    private lateinit var tabConfigurationStrategy: TabLayoutMediator.TabConfigurationStrategy
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2 = findViewById(R.id.viewpager)
        tabLayout = findViewById(R.id.tab)
        entryFragment = EntryFragment()
        analysisFragment = AnalysisFragment()
        graphFragment = GraphFragment()
        extraFragment = ExtraFragment()

        fragments = ArrayList()
        fragments.add(entryFragment)
        fragments.add(analysisFragment)
        fragments.add(graphFragment)
        fragments.add(extraFragment)

        fragmentStateAdapter = FragmentStateAdapter(this, fragments)
        viewPager2.adapter = fragmentStateAdapter

        tabConfigurationStrategy =
            TabLayoutMediator.TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.text = tabTitles[position]
            }
        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2, tabConfigurationStrategy)
        tabLayoutMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }
}