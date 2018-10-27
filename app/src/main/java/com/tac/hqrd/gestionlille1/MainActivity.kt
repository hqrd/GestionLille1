package com.tac.hqrd.gestionlille1

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.tac.hqrd.gestionlille1.ui.main.AddFragment

class MainActivity : AppCompatActivity() {
    companion object {
        const val backstactroottag = "root_activity"

        fun addFragmentFromFragment(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            currentFragment: Fragment,
            addToBackstack: Boolean = true
        ) {
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            currentFragment.onPause()
            if (addToBackstack)
                transaction.addToBackStack(MainActivity.backstactroottag)
            transaction.commitAllowingStateLoss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportFragmentManager?.popBackStack(backstactroottag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, AddFragment.newInstance())
            ?.addToBackStack(backstactroottag)
            ?.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }


}
