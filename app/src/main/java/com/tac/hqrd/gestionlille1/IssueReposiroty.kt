package com.tac.hqrd.gestionlille1

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.tac.hqrd.gestionlille1.db.IssuesDatabase
import com.tac.hqrd.gestionlille1.db.dao.IssueDao
import com.tac.hqrd.gestionlille1.db.entity.Issue

class IssueRepository(application: Application) {

    private val issueDao: IssueDao
    private val listLiveData: LiveData<List<Issue>>

    init {
        val issueRoomDatabase = IssuesDatabase.getDatabase(application)
        issueDao = issueRoomDatabase?.issueDao()!!
        listLiveData = issueDao.getAll()
    }

    fun getAllIssues(): LiveData<List<Issue>> {
        return listLiveData
    }

    fun insert(issue: Issue) {
        InsertAsyncTask(issueDao).execute(issue)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: IssueDao) :
        AsyncTask<Issue, Void, Void>() {

        override fun doInBackground(vararg params: Issue): Void? {
            Log.d("BACKGROUND", params.toString())
            mAsyncTaskDao.insertAll(params[0])
            return null
        }
    }

}