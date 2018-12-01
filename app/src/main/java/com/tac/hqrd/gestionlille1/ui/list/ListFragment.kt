package com.tac.hqrd.gestionlille1.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.databinding.ListFragmentBinding
import com.tac.hqrd.gestionlille1.ui.adapter.IssueListAdapter
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel
import kotlinx.android.synthetic.main.list_fragment.*


class ListFragment : Fragment() {

    private lateinit var viewModel: ListIssuesViewModel
    private lateinit var binding: ListFragmentBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: IssueListAdapter

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

        viewModel.issues.observe(viewLifecycleOwner,
            Observer<List<Any>> {
                viewModel.updateNumberIssues()
                binding.viewmodel = viewModel
                mAdapter = IssueListAdapter(viewModel.issues.value!!)
                listIssues.adapter = mAdapter
            })

        binding.viewmodel = viewModel

        linearLayoutManager = LinearLayoutManager(activity)
        binding.listIssues.layoutManager = linearLayoutManager

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navFrament = findNavController()

    }

}
