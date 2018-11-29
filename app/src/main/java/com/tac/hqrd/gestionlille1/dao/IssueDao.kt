package com.tac.hqrd.gestionlille1.dao

import androidx.room.*
import com.tac.hqrd.gestionlille1.model.Issue

@Dao
interface IssueDao {
    @Query("SELECT * FROM Issue")
    fun getAll(): List<Issue>

    @Query("SELECT * FROM Issue WHERE uid IN (:IssueIds)")
    fun loadAllByIds(IssueIds: IntArray): List<Issue>

    @Query(
        "SELECT * FROM Issue WHERE adress LIKE :first LIMIT 1"
    )
    fun findByAdress(first: String): Issue

    @Update
    fun updateUsers(vararg Issues: Issue)

    @Insert
    fun insertAll(vararg Issues: Issue)

    @Insert
    fun insertAll(Issues: List<Issue>)

    @Delete
    fun delete(Issue: Issue)
}