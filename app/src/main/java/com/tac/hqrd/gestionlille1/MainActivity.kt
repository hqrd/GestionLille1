package com.tac.hqrd.gestionlille1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var navFrament: NavController
    private lateinit var viewModel: ListIssuesViewModel

    companion object {
        lateinit var googleMap: GoogleMap
    }

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

    fun setDisplayHomeAsUpEnabled(b: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(b)
    }

    /**
     * We check the permissiosn on start
     */
    override fun onStart() {
        super.onStart()
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION) || !isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1
            )
        }
    }

    private fun isPermissionGranted(permission: String) =
        (ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED)

    /**
     * We loop on the permission request
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //permissions OK
                } else {
                    Snackbar.make(
                        main_coordlayout, resources.getText(R.string.loc_required), Snackbar.LENGTH_LONG
                    ).show()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                        1
                    )
                }
                return
            }
        }
    }
}
