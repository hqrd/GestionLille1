package com.tac.hqrd.gestionlille1.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tac.hqrd.gestionlille1.R
import kotlinx.android.synthetic.main.list_fragment.*
import java.util.logging.Logger

class ListFragment : Fragment() {

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

        val navFrament = findNavController()
        button2.setOnClickListener { _ ->
            navFrament.navigate(R.id.action_to_addFragment)
        }

        val test = ListFragmentArgs.fromBundle(arguments).test
        Logger.getLogger(javaClass.toString()).info("TEST = $test")
    }

}
