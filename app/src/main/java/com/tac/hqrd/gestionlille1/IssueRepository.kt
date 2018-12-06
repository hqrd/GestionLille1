package com.tac.hqrd.gestionlille1

import android.app.Application
import androidx.lifecycle.LiveData
import com.tac.hqrd.gestionlille1.db.IssuesDatabase
import com.tac.hqrd.gestionlille1.db.dao.IssueDao
import com.tac.hqrd.gestionlille1.db.entity.Issue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IssueRepository(application: Application) {

    private val issueDao: IssueDao
    private val listLiveData: LiveData<List<Issue>>

    init {
        val issueRoomDatabase = IssuesDatabase.getDatabase(application)
        issueDao = issueRoomDatabase.issueDao()
        listLiveData = issueDao.getAll()
    }

    fun getAllIssues(): LiveData<List<Issue>> {
        return listLiveData
    }

    fun insert(issue: Issue) {
        GlobalScope.launch {
            issueDao.insertAll(issue)
        }
    }

    fun cleanDB() {
        GlobalScope.launch {
            issueDao.deleteAll()
        }
    }

    fun delete(issue: Issue) {
        GlobalScope.launch {
            issueDao.delete(issue)
        }
    }

}