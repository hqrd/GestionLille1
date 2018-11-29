package com.tac.hqrd.gestionlille1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel

class MainActivity() : AppCompatActivity() {
    lateinit var navFrament: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navFrament = findNavController(R.id.nav_host_fragment)

        val db = IssuesDatabase.getInstance(this)

        if (db?.issueDao()?.getAll().isNullOrEmpty()) {
            var viewModel = ViewModelProviders.of(this).get(ListIssuesViewModel::class.java)
            viewModel.loadIssues()
            db?.issueDao()?.insertAll(viewModel.issues)
        }
        var issues = db?.issueDao()?.getAll()
        Log.d("MainActivity", issues.toString())

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigation.setupWithNavController(navFrament)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

}
