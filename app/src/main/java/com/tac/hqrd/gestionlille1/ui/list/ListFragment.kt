package com.tac.hqrd.gestionlille1.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.databinding.ListFragmentBinding
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import java.util.logging.Logger


class ListFragment : Fragment() {

    private lateinit var viewModel: ListIssuesViewModel
    private lateinit var binding: ListFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ListIssuesViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        val view = binding.root

        binding.viewmodel = viewModel

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val navFrament = findNavController()
        button2.setOnClickListener { _ ->
            Logger.getLogger(javaClass.toString()).info("VM = ${viewModel}")
        }

        val test = ListFragmentArgs.fromBundle(arguments).test
        Logger.getLogger(javaClass.toString()).info("TEST = $test")

    }

}
