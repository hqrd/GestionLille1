package com.tac.hqrd.gestionlille1.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import java.util.logging.Logger


class ListFragment : Fragment() {

    private lateinit var viewModel: ListIssuesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ListIssuesViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val navFrament = findNavController()
        button2.setOnClickListener { _ ->
            Logger.getLogger(javaClass.toString()).info("VM = ${viewModel}")
        }

        val test = ListFragmentArgs.fromBundle(arguments).test
        Logger.getLogger(javaClass.toString()).info("TEST = $test")

        viewModel.elapsedtime.observe(this, Observer { timer ->
            message.text = timer.toString()
            Logger.getLogger(javaClass.toString()).info("Updating timer")
        })
    }

}
