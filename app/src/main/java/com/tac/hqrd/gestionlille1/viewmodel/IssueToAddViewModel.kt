package com.tac.hqrd.gestionlille1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tac.hqrd.gestionlille1.db.IssueType
import com.tac.hqrd.gestionlille1.db.entity.Issue


class IssueToAddViewModel : ViewModel() {
    val issue: MutableLiveData<Issue> = MutableLiveData()

    init {
        issue.value = Issue(IssueType.OTHER, 0f, 0f)
    }

    override fun toString(): String {
        return "IssueToAddViewModel(issue=${issue.value})"
    }


}

