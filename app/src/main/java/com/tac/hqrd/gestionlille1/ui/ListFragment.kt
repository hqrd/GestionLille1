package com.tac.hqrd.gestionlille1.ui

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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.databinding.ListFragmentBinding
import com.tac.hqrd.gestionlille1.helper.LocationHelper
import com.tac.hqrd.gestionlille1.helper.ScrollFragmentHelper
import com.tac.hqrd.gestionlille1.ui.adapter.IssueListAdapter
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ListFragment : Fragment(), OnMapReadyCallback {

    private lateinit var viewModel: ListIssuesViewModel
    private lateinit var binding: ListFragmentBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: IssueListAdapter
    private var mLat: Double = 0.0
    private var mLong: Double = 0.0

    private var gmap: GoogleMap? = null

    override fun onMapReady(googleMap: GoogleMap?) {
        gmap = googleMap
        gmap?.setMinZoomPreference(12F)
        val ny = LatLng(40.7143528, -74.0059731)
        gmap?.moveCamera(CameraUpdateFactory.newLatLng(ny))
    }

    override fun onResume() {
        super.onResume()
        ScrollFragmentHelper.startScroll(activity)
        if (::mAdapter.isInitialized)
            mAdapter.onResume()
    }

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

        val self = this
        GlobalScope.launch {
            LocationHelper.getLastLoc(activity!!, false) { adresses ->
                getView()?.let {
                    //to make sure we're on the page
                    if (!adresses.isEmpty()) {
                        mLat = adresses[0].latitude
                        mLong = adresses[0].longitude
                    }
                    viewModel.issues.observe(viewLifecycleOwner,
                        Observer<List<Any>> {
                            viewModel.updateNumberIssues()
                            binding.viewmodel = viewModel
                            mAdapter = IssueListAdapter(viewModel.issues.value!!, mLat, mLong, self)
                            listIssues.adapter = mAdapter
                        })
                    loader.hide()
                    if (viewModel.issues.value.isNullOrEmpty()) {
                        textListEmpty.text = getString(R.string.no_problem)
                    } else {
                        textListEmpty.text = ""
                    }
                }

            }
        }

        binding.viewmodel = viewModel

        linearLayoutManager = LinearLayoutManager(activity)
        binding.listIssues.layoutManager = linearLayoutManager

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navFrament = findNavController()

        buttonUp.setOnClickListener {
            //todo scroll top
        }

        loader.show()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        if (::mAdapter.isInitialized)
            mAdapter.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        if (::mAdapter.isInitialized)
            mAdapter.onPause()
    }

    override fun onStart() {
        super.onStart()
        if (::mAdapter.isInitialized)
            mAdapter.onStart()
    }

    override fun onStop() {
        super.onStop()
        if (::mAdapter.isInitialized)
            mAdapter.onStop()
    }
}
