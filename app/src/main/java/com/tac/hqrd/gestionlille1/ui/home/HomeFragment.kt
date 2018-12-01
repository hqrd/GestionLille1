package com.tac.hqrd.gestionlille1.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.databinding.HomeFragmentBinding
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {

    private lateinit var viewModel: ListIssuesViewModel
    private lateinit var binding: HomeFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val tb = activity!!.findViewById(R.id.main_appbar) as AppBarLayout
        tb.setExpanded(true, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        val view = binding.root

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ListIssuesViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.issues.observe(viewLifecycleOwner,
            Observer<List<Any>> {
                viewModel.updateNumberIssues()
                binding.viewmodel = viewModel
            })

        binding.viewmodel = viewModel



        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navFrament = findNavController()
        buttonAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddFragment()
            navFrament.navigate(action)
        }
        buttonView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToListFragment()
            navFrament.navigate(action)
        }
    }

}
