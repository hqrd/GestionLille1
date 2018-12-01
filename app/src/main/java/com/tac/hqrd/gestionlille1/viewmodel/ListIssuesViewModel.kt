package com.tac.hqrd.gestionlille1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.tac.hqrd.gestionlille1.IssueRepository
import com.tac.hqrd.gestionlille1.db.IssueType
import com.tac.hqrd.gestionlille1.db.entity.Issue
import kotlin.random.Random


class ListIssuesViewModel(application: Application) : AndroidViewModel(application) {
    private val issueRepository: IssueRepository
    internal val issues: LiveData<List<Issue>>

    var numberIssues: String = "0"

    init {
        issueRepository = IssueRepository(application)
        issues = issueRepository.getAllIssues()
    }

    fun insert(issue: Issue) {
        issueRepository.insert(issue)
    }

    fun addIssue() {
        this.insert(Issue(IssueType.OTHER, Random.nextFloat(), Random.nextFloat()))
    }

    fun cleanDB() {
        issueRepository.cleanDB()
    }


    fun updateNumberIssues() {
        numberIssues = if (issues.value?.size.toString() != "null") issues.value?.size.toString() else "0"
    }

    override fun toString(): String {
        return "ListIssuesViewModel(issues=${issues.value}, numberIssues='$numberIssues')"
    }

}

