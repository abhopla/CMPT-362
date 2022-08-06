package com.example.dolladollabills

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.dolladollabills.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.ArrayList

//class MainActivity : AppCompatActivity() {
////    private lateinit var entryFragment: EntryFragment
////    private lateinit var analysisFragment: AnalysisFragment
////    private lateinit var goalFragment: GoalFragment
////    private lateinit var extraFragment: ExtraFragment
////    private lateinit var viewPager2: ViewPager2
////    private lateinit var tabLayout: TabLayout
////    private lateinit var fragmentStateAdapter: FragmentStateAdapter
////    private lateinit var fragments: ArrayList<Fragment>
////    private val tabTitles = arrayOf("Entry", "Analysis", "Goals", "Extra") //Tab titles
////    private lateinit var tabConfigurationStrategy: TabLayoutMediator.TabConfigurationStrategy
////    private lateinit var tabLayoutMediator: TabLayoutMediator
//    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setSupportActionBar(binding.appBarMain.toolbar)
//
//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
//        val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home, R.id.nav_input, R.id.nav_analysis, R.id.nav_goal, R.id.nav_extra
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
////        setContentView(R.layout.activity_main)
////
////        viewPager2 = findViewById(R.id.viewpager)
////        tabLayout = findViewById(R.id.tab)
////        entryFragment = EntryFragment()
////        analysisFragment = AnalysisFragment()
////        goalFragment = GoalFragment()
////        extraFragment = ExtraFragment()
////
////        fragments = ArrayList()
////        fragments.add(entryFragment)
////        fragments.add(analysisFragment)
////        fragments.add(goalFragment)
////        fragments.add(extraFragment)
////
////        fragmentStateAdapter = FragmentStateAdapter(this, fragments)
////        viewPager2.adapter = fragmentStateAdapter
////
////        tabConfigurationStrategy =
////            TabLayoutMediator.TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
////                tab.text = ""
////            }
////        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2, tabConfigurationStrategy)
////        tabLayoutMediator.attach()
////        tabLayout.getTabAt(0)?.setIcon(R.drawable.editicon)
////        tabLayout.getTabAt(1)?.setIcon(R.drawable.graphicon)
////        tabLayout.getTabAt(2)?.setIcon(R.drawable.goalsicon)
////        tabLayout.getTabAt(3)?.setIcon(R.drawable.setttingicon)
//    }
//
//}


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            val intent= Intent()
            intent.action= Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"New goal: Create a budget and save $500 a month")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share To:"))
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_input, R.id.nav_analysis, R.id.nav_goal, R.id.nav_extra
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}