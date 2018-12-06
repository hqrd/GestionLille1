package com.tac.hqrd.gestionlille1.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tac.hqrd.gestionlille1.MainActivity
import com.tac.hqrd.gestionlille1.MainActivity.Companion.googleMap
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.databinding.IssueDetailsFragmentBinding
import com.tac.hqrd.gestionlille1.db.entity.Issue
import com.tac.hqrd.gestionlille1.helper.ScrollFragmentHelper
import com.tac.hqrd.gestionlille1.viewmodel.ListIssuesViewModel
import kotlinx.android.synthetic.main.issue_details_fragment.*

class IssueDetailsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: IssueDetailsFragmentBinding
    private lateinit var viewModel: ListIssuesViewModel
    private var uidIssue: Long = -1
    private lateinit var mIssue: Issue
    private lateinit var mainActivity: MainActivity

    override fun onResume() {
        super.onResume()
        ScrollFragmentHelper.stopScroll(activity)
        mapViewDetailIssue?.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        mainActivity.setDisplayHomeAsUpEnabled(true)
        mapViewDetailIssue?.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.setDisplayHomeAsUpEnabled(false)
        mapViewDetailIssue?.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.issue_details_fragment, container, false)
        val view = binding.root
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(ListIssuesViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uidIssue = IssueDetailsFragmentArgs.fromBundle(arguments).uidIssue
        viewModel.issues.value?.forEach {
            if (it.uid == uidIssue) {
                mIssue = it
                binding.issue = mIssue
                titleCard.text = mIssue.type.stringType
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val navFrament = findNavController()

        mapViewDetailIssue.onCreate(null)
        mapViewDetailIssue.getMapAsync(this)

        buttonViewOnGmap.setOnClickListener {
            openGmap()
        }

        buttonDeleteIssue.setOnClickListener {
            viewModel.delete(mIssue)
            navFrament.navigateUp()
            Toast.makeText(activity, resources.getText(R.string.deleting_task), Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGmap() {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.google.com/maps/search/?api=1&query=${mIssue.latGps},${mIssue.longGps}")
        startActivity(openURL)
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        val position = LatLng(mIssue.latGps, mIssue.longGps)
        googleMap.addMarker(
            MarkerOptions().position(position).title(mIssue.adress)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        googleMap.setMinZoomPreference(15F)

    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapViewDetailIssue?.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapViewDetailIssue?.onPause()
    }

    override fun onStart() {
        super.onStart()
        mapViewDetailIssue?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapViewDetailIssue?.onStop()
    }
}
