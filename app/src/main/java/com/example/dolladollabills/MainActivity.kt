package com.example.dolladollabills

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.dolladollabills.databinding.ActivityMainBinding
import com.example.dolladollabills.db.goals.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var goalViewmodel: GoalViewModel
    private lateinit var goalListAdapter: GoalListAdapter
    private lateinit var database: GoalDatabase
    private lateinit var databaseDao: GoalDatabaseDao
    private lateinit var repository: GoalRepository
    private lateinit var viewModelFactory: GoalViewModelFactory
    private lateinit var goalsArr: MutableList<String>
    private lateinit var listView: ListView
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        goalsArr = mutableListOf<String>()
        database = GoalDatabase.getInstance(this)
        databaseDao = database.goalDatabaseDao
        repository = GoalRepository(databaseDao)
        viewModelFactory = GoalViewModelFactory(repository)
        goalViewmodel = ViewModelProvider(this, viewModelFactory).get(GoalViewModel::class.java)

        goalViewmodel.allGoalsLiveData.observe(this, Observer { goals ->
            goalsArr = mutableListOf<String>()
            goals.forEach {
               goalsArr.add(it.name)
            }

        })
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
        binding.appBarMain.fab.setOnClickListener { view ->
            if(goalsArr.isEmpty()){
                Snackbar.make(view, "Please make a goal first", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }else{
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                var lastItem = goalsArr.lastIndex
                println("DEBUG my goals ${goalsArr[lastItem]}")
                println("DEBUG full array ${goalsArr}")
                intent.putExtra(Intent.EXTRA_TEXT,"New Goal: ${goalsArr[lastItem]}")
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))

            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
