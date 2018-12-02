package com.tac.hqrd.gestionlille1.ui.adapter


import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.db.entity.Issue
import com.tac.hqrd.gestionlille1.helper.LocationHelper
import kotlinx.android.synthetic.main.issue_card.view.*


class IssueListAdapter(private val issues: List<Issue>, private val mLat: Double, private val mLong: Double) :
    RecyclerView.Adapter<IssueListAdapter.IssueHolder>() {
    companion object {
        lateinit var googleMap: GoogleMap
    }

    private lateinit var holder: IssueHolder

    override fun getItemCount(): Int {
        return issues.size
    }

    override fun onBindViewHolder(holder: IssueListAdapter.IssueHolder, position: Int) {
        val itemIssue = issues[position]
        holder.bindIssue(itemIssue, mLat, mLong)
        holder.onResume()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueListAdapter.IssueHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.issue_card, parent, false)
        holder = IssueHolder(inflatedView)
        return holder
    }

    fun onResume() {
        if (::holder.isInitialized)
            holder.onResume()
    }

    class IssueHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener, OnMapReadyCallback {

        private var view: View = v
        private var issue: Issue? = null
        private val mapView: MapView


        init {
            v.setOnClickListener { this }
            mapView = v.findViewById(R.id.mapView) as MapView
            mapView.onCreate(null)
            mapView.getMapAsync(this)
        }

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }

        @SuppressLint("SetTextI18n")//todo concat in xml
        fun bindIssue(issue: Issue, mLat: Double, mLong: Double) {
            this.issue = issue

            if (mLat != 0.0 && mLong != 0.0) {
                view.titleCard.text = issue.type.stringType + " (" +
                        Math.round(LocationHelper.distance(mLat, issue.latGps, mLong, issue.longGps)) +
                        " mètres)"
            } else {
                view.titleCard.text = issue.type.stringType
            }
            view.adressCard.text = issue.adress
            view.descCard.text = issue.description

            //todo map
        }

        override fun onMapReady(gMap: GoogleMap) {
            googleMap = gMap
            val position = LatLng(issue!!.latGps, issue!!.longGps)
            googleMap.addCircle(
                CircleOptions().center(position).radius(40.0).fillColor(Color.GRAY).strokeColor(Color.GRAY).clickable(
                    false
                )
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
            googleMap.setMinZoomPreference(13F)

        }

        fun onResume() {
            mapView.onResume()
        }
    }
}