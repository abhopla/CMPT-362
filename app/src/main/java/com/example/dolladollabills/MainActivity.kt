package com.example.dolladollabills

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var entryFragment: EntryFragment
    private lateinit var analysisFragment: AnalysisFragment
    private lateinit var goalFragment: GoalFragment
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
        goalFragment = GoalFragment()
        extraFragment = ExtraFragment()

        fragments = ArrayList()
        fragments.add(entryFragment)
        fragments.add(analysisFragment)
        fragments.add(goalFragment)
        fragments.add(extraFragment)

        fragmentStateAdapter = FragmentStateAdapter(this, fragments)
        viewPager2.adapter = fragmentStateAdapter

        tabConfigurationStrategy =
            TabLayoutMediator.TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.text = ""
            }
        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2, tabConfigurationStrategy)
        tabLayoutMediator.attach()
        tabLayout.getTabAt(0)?.setIcon(R.drawable.editicon)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.graphicon)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.goalsicon)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.setttingicon)


    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.add(0, 0, 0, "Receipt Scanner").setShowAsAction(2)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            0 -> {
                val myIntent = Intent(this, ReceiptScanner::class.java)
                this.startActivity(myIntent)
                true
            }
            else -> {
                finish()
                false
            }
        }
    }
}