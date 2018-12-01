package com.tac.hqrd.gestionlille1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel

class MainActivity : AppCompatActivity() {
    lateinit var navFrament: NavController
    private lateinit var viewModel: ListIssuesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navFrament = findNavController(R.id.nav_host_fragment)

        viewModel = ViewModelProviders.of(this).get(ListIssuesViewModel::class.java)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigation.setupWithNavController(navFrament)

        //        buttonClean.setOnClickListener { _ ->
//            viewModel.cleanDB()
//            Logger.getLogger(javaClass.toString()).info("VM = ${viewModel}")
//        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

}
