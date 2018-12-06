package com.tac.hqrd.gestionlille1.viewmodel

import android.app.Activity
import android.app.Application
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.tac.hqrd.gestionlille1.IssueRepository
import com.tac.hqrd.gestionlille1.R
import com.tac.hqrd.gestionlille1.db.IssueType
import com.tac.hqrd.gestionlille1.db.entity.Issue
import com.tac.hqrd.gestionlille1.helper.LocationHelper

/**
 * Viewmodel representing the list of issues in the DB
 */
class ListIssuesViewModel(application: Application) : AndroidViewModel(application) {
    private val issueRepository: IssueRepository =
        IssueRepository(application)
    internal val issues: LiveData<List<Issue>>

    var numberIssues: String = "0"

    init {
        issues = issueRepository.getAllIssues()
    }

    fun insert(issue: Issue) {
        issueRepository.insert(issue)
    }

    suspend fun addGeneratedIssue(activity: Activity) {
        LocationHelper.getLastLoc(activity, true) { adresses ->
            if (adresses != null) {
                if (adresses.isEmpty()) {
                    if (Looper.myLooper() == null) {
                        Looper.prepare()
                    }
                    Toast.makeText(activity, activity.resources.getText(R.string.no_adress_found), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val issue = Issue(IssueType.randomType(), adresses[0].latitude, adresses[0].longitude)
                    issue.adress = adresses[0].getAddressLine(0)
                    issue.description = "Description générée automatiquement"
                    this.insert(issue)
                }
            }
        }
    }

    fun cleanDB() {
        issueRepository.cleanDB()
    }

    fun delete(issue: Issue) {
        issueRepository.delete(issue)
    }

    fun updateNumberIssues() {
        numberIssues = if (issues.value?.size.toString() != "null") issues.value?.size.toString() else "0"
    }

    override fun toString(): String {
        return "ListIssuesViewModel(issues=${issues.value}, numberIssues='$numberIssues')"
    }


}

