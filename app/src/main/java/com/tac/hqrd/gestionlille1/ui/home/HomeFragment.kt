package com.tac.hqrd.gestionlille1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.ui.list.ListIssuesViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.logging.Logger

class HomeFragment : Fragment() {

    private lateinit var viewModel: ListIssuesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ListIssuesViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.getElapsedTime().observe(this, Observer { timer ->
            messageHome.text = timer.toString()
            Logger.getLogger(javaClass.toString()).info("Updating timer")
        })

        val navFrament = findNavController()
        buttonAdd.setOnClickListener { _ ->
            val action = HomeFragmentDirections.actionHomeFragmentToAddFragment()
            navFrament.navigate(action)
        }


    }

}
