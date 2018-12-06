package com.tac.hqrd.gestionlille1.ui

import android.location.Address
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.databinding.AddFragmentBinding
import com.tac.hqrd.gestionlille1.db.IssueType
import com.tac.hqrd.gestionlille1.db.entity.Issue
import com.tac.hqrd.gestionlille1.helper.LocationHelper
import com.tac.hqrd.gestionlille1.helper.ScrollFragmentHelper
import com.tac.hqrd.gestionlille1.viewmodel.IssueToAddViewModel
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel
import kotlinx.android.synthetic.main.add_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddFragment : Fragment(), AdapterView.OnItemSelectedListener {
    companion object {
        private const val TAG: String = "AddFragment"
    }

    private lateinit var binding: AddFragmentBinding
    private lateinit var viewModel: ListIssuesViewModel
    private lateinit var issueViewmodel: IssueToAddViewModel

    private var mAddress: String = ""
    private var mLat: Double = 0.0
    private var mLong: Double = 0.0

    override fun onResume() {
        super.onResume()
        ScrollFragmentHelper.stopScroll(activity)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //We bind the IssueToAddViewModel to the layout
        binding = DataBindingUtil.inflate(inflater, R.layout.add_fragment, container, false)
        val view = binding.root

        activity?.run {
            viewModel = ViewModelProviders.of(this).get(ListIssuesViewModel::class.java)
            issueViewmodel = ViewModelProviders.of(this).get(IssueToAddViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        issueViewmodel.issue.observe(viewLifecycleOwner,
            Observer<Any> {
                binding.issue = issueViewmodel.issue.value
                Log.d(TAG, binding.issue.toString())
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

        buttonSave.setOnClickListener {
            checkAndSaveIssue()
        }

        buttonLoc.setOnClickListener {
            updateAddress(true)
        }

        //We create the spinner with issue's types
        createSpinnerIssueType()

        if (issueViewmodel.issue.value?.adress == "")
            updateAddress()
    }

    private fun checkAndSaveIssue() {
        if (checkLatLong()) {
            viewModel.insert(issueViewmodel.issue.value!!)
            issueViewmodel.issue.postValue(Issue(IssueType.TREE_TO_TRIM, 0.0, 0.0))

            val navFrament = findNavController()
            val action = AddFragmentDirections.actionToListFragment()
            navFrament.navigate(action)
        } else {
            Toast.makeText(activity, resources.getText(R.string.fields_missing), Toast.LENGTH_SHORT).show()
        }
    }

    private fun createSpinnerIssueType() {
        ArrayAdapter.createFromResource(
            context!!, R.array.issue_type_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }
        spinnerType.onItemSelectedListener = this
    }

    private fun checkLatLong(): Boolean {
        return issueViewmodel.issue.value?.let {
            it.latGps != 0.0 && it.longGps != 0.0
        } ?: false
    }

    /**
     * Listener of the spinner for the type of issue
     */
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        parent.getItemAtPosition(pos)
        issueViewmodel.issue.apply {
            val issue: Issue = value!!
            issue.type = IssueType.fromString(parent.getItemAtPosition(pos).toString())
            postValue(issue)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
    }

    /**
     * Updates the internal and displayed address
     */
    private fun updateAddress(around: Boolean = false) {
        buttonLoc.isEnabled = false
        editTextAddress.isEnabled = false
        GlobalScope.launch {
            LocationHelper.getLastLoc(activity!!, around) { adresses ->
                displayAddress(adresses)

                activity?.runOnUiThread {
                    editTextAddress?.isEnabled = true
                    buttonLoc?.isEnabled = true
                }
            }

        }

    }

    private fun displayAddress(adresses: List<Address>?) {
        if (adresses != null) {
            if (adresses.isEmpty()) {
                mAddress = ""
                mLat = 0.0
                mLong = 0.0
                if (Looper.myLooper() == null) {
                    Looper.prepare()
                }
                Toast.makeText(activity, resources.getText(R.string.no_adress_found), Toast.LENGTH_SHORT).show()
            } else {
                mAddress = adresses[0].getAddressLine(0)
                mLat = adresses[0].latitude
                mLong = adresses[0].longitude
            }
        }

        issueViewmodel.issue.apply {
            val issue: Issue = value!!
            issue.adress = mAddress
            issue.latGps = mLat
            issue.longGps = mLong
            postValue(issue)
        }
    }

}
