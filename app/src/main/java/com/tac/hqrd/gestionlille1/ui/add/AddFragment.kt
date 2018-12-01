package com.tac.hqrd.gestionlille1.ui.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel
import kotlinx.android.synthetic.main.add_fragment.*

class AddFragment : Fragment() {

    private lateinit var viewModel: ListIssuesViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val tb = activity!!.findViewById(R.id.main_appbar) as AppBarLayout
        tb.setExpanded(true, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(ListIssuesViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.add_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navFrament = findNavController()
        button.setOnClickListener {
            viewModel.addIssue()
            val action = AddFragmentDirections.actionToListFragment()
            navFrament.navigate(action)
        }
    }

}
