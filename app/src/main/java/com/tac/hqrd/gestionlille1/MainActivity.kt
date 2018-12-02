package com.tac.hqrd.gestionlille1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var navFrament: NavController
    private lateinit var viewModel: ListIssuesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(findViewById(R.id.main_toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)

        navFrament = findNavController(R.id.nav_host_fragment)

        viewModel = ViewModelProviders.of(this).get(ListIssuesViewModel::class.java)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigation.setupWithNavController(navFrament)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_delete_all -> {
            Toast.makeText(this, resources.getText(R.string.deleting_tasks), Toast.LENGTH_SHORT).show()
            viewModel.cleanDB()
            true
        }

        R.id.action_fixtures -> {
            val self = this
            Toast.makeText(this, resources.getText(R.string.adding_fixtures), Toast.LENGTH_SHORT).show()
            GlobalScope.launch {
                repeat(5) {
                    viewModel.addGeneratedIssue(self)
                }
            }
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

}
