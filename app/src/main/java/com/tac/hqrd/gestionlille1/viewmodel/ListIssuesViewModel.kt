package com.tac.hqrd.gestionlille1.viewmodel

import androidx.lifecycle.ViewModel
import com.tac.hqrd.gestionlille1.model.Issue
import kotlin.random.Random


class ListIssuesViewModel() : ViewModel() {
    var issues: ArrayList<Issue> = ArrayList()
    var numberIssues: String = "0"

    init {
        loadIssues()
    }

    fun addIssue() {
        issues.add(
            Issue(
                numberIssues.toLong(), "Autre", Random.nextFloat()
                , Random.nextFloat()
            )
        )
        updateNumberIssues()
    }

    fun loadIssues() {
        issues = ArrayList(listOf(Issue(1, "Autre", 10F, 15F), Issue(2, "Détritus", 12F, 10F)))
        updateNumberIssues()
    }

    private fun updateNumberIssues() {
        numberIssues = issues.size.toString()
    }

    override fun toString(): String {
        return "ListIssuesViewModel(issues=$issues, numberIssues='$numberIssues')"
    }

}

