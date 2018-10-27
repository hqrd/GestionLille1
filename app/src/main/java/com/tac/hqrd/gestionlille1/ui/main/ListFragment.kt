package com.tac.hqrd.gestionlille1.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tac.hqrd.gestionlille1.MainActivity.Companion.addFragmentFromFragment
import com.tac.hqrd.gestionlille1.R
import kotlinx.android.synthetic.main.list_fragment.*
import java.util.logging.Logger

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        button2.setOnClickListener { _ ->
            fragmentManager?.let { fragmentManager ->
                addFragmentFromFragment(
                    fragmentManager,
                    AddFragment.newInstance(),
                    this
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Logger.getLogger(javaClass.toString()).info("RESUME")
    }
}
