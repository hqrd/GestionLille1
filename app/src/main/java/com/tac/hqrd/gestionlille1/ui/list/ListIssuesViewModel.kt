package com.tac.hqrd.gestionlille1.ui.list

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tac.hqrd.gestionlille1.model.Issue
import java.util.*


class ListIssuesViewModel : ViewModel() {
    private lateinit var issues: MutableLiveData<List<Issue>>
    private lateinit var mElapsedTime: MutableLiveData<Long>
    private var mInitialTime: Long = SystemClock.elapsedRealtime()
    private val ONE_SECOND: Long = 1000

    init {
        mInitialTime = SystemClock.elapsedRealtime()
    }

    fun getElapsedTime(): LiveData<Long> {
        if (!::mElapsedTime.isInitialized) {
            mElapsedTime = MutableLiveData()
            startTimer()
        }
        return mElapsedTime
    }

    fun getIssues(): LiveData<List<Issue>> {
        if (!::issues.isInitialized) {
            issues = MutableLiveData()
            loadIssues()
        }
        return issues
    }

    private fun startTimer() {
        mElapsedTime.postValue((SystemClock.elapsedRealtime() - mInitialTime) / 1000)
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {// setValue() cannot be called from a background thread so postValue is used.// Update the elapsed time every second.
                val newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000
                // setValue() cannot be called from a background thread so postValue is used.
                mElapsedTime.postValue(newValue)
            }
        }, ONE_SECOND, ONE_SECOND)
    }

    private fun loadIssues() {
        // Do an asynchronous operation to fetch issues.
        val items = listOf(Issue(1, "Autre", 10F, 15F), Issue(2, "Détritus", 12F, 10F))
        issues.postValue(items)
    }

    override fun toString(): String {
        return "ListIssuesViewModel(issues=${issues.value}, mElapsedTime=${mElapsedTime.value}, mInitialTime=$mInitialTime, ONE_SECOND=$ONE_SECOND)"
    }


}

