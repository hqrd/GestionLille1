package com.tac.hqrd.gestionlille1.ui.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.db.entity.Issue
import kotlinx.android.synthetic.main.issue_card.view.*

class IssueListAdapter(private val issues: List<Issue>) : RecyclerView.Adapter<IssueListAdapter.IssueHolder>() {

    override fun getItemCount(): Int {
        return issues.size
    }

    override fun onBindViewHolder(holder: IssueListAdapter.IssueHolder, position: Int) {
        val itemIssue = issues[position]
        holder.bindIssue(itemIssue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueListAdapter.IssueHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.issue_card, parent, false)
        return IssueHolder(inflatedView)
    }

    class IssueHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var issue: Issue? = null

        init {
            v.setOnClickListener { this }
        }

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindIssue(issue: Issue) {
            this.issue = issue
//            Picasso.with(view.context).load(issues.issue_image_url).into(view.iv_cover)
            view.titleCard.text = issue.type.stringType
            view.adressCard.text = issue.latGps.toString()
            view.descCard.text = issue.description
        }
    }
}