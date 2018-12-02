package com.tac.hqrd.gestionlille1.ui.add

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.databinding.AddFragmentBinding
import com.tac.hqrd.gestionlille1.db.entity.Issue
import com.tac.hqrd.gestionlille1.helper.LocationHelper
import com.tac.hqrd.gestionlille1.helper.ScrollFragmentHelper
import com.tac.hqrd.gestionlille1.viewmodel.IssueToAddViewModel
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel
import kotlinx.android.synthetic.main.add_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddFragment : Fragment() {
    companion object {
        private const val TAG: String = "AddFragment"
    }

    private lateinit var viewModel: ListIssuesViewModel
    private var mAddress: String = ""
    private lateinit var issueViewmodel: IssueToAddViewModel
    private lateinit var binding: AddFragmentBinding

    override fun onResume() {
        super.onResume()
        ScrollFragmentHelper.stopScroll(activity)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_fragment, container, false)
        val view = binding.root

        activity?.run {
            viewModel = ViewModelProviders.of(this).get(ListIssuesViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        issueViewmodel = ViewModelProviders.of(this).get(IssueToAddViewModel::class.java)

        issueViewmodel.issue.observe(viewLifecycleOwner,
            Observer<Any> {
                binding.issue = issueViewmodel.issue.value
                Log.d(Companion.TAG, binding.issue.toString())
            })
        viewModel.issues.observe(viewLifecycleOwner,
            Observer<List<Any>> {
                viewModel.updateNumberIssues()
            })

        binding.issue = issueViewmodel.issue.value


        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val navFrament = findNavController()

        button.setOnClickListener {
            viewModel.addIssue()
            val action = AddFragmentDirections.actionToListFragment()
            navFrament.navigate(action)
        }
        buttonLoc.setOnClickListener {
            updateAddress(true)
        }

        updateAddress()
    }


    private fun updateAddress(around: Boolean = false) {
        GlobalScope.launch {
            editTextAddress.isEnabled = false
            LocationHelper.getLastLoc(activity!!, around) { adresses ->
                if (adresses.isEmpty()) {
                    mAddress = ""
                    if (Looper.myLooper() == null) {
                        Looper.prepare()
                    }
                    Toast.makeText(activity, resources.getText(R.string.no_adress_found), Toast.LENGTH_SHORT).show()
                } else {
                    mAddress = adresses[0].getAddressLine(0)
                }
                issueViewmodel.issue.apply {
                    val issue: Issue = value!!
                    issue.adress = mAddress
                    value = issue
                }
                editTextAddress.isEnabled = true
            }
        }
    }


}
