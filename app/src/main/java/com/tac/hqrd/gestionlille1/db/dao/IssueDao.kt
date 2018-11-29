package com.tac.hqrd.gestionlille1.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tac.hqrd.gestionlille1.db.entity.Issue

@Dao
interface IssueDao {
    @Query("SELECT * FROM Issue")
    fun getAll(): LiveData<List<Issue>>

    @Insert
    fun insertAll(vararg issues: Issue)

    @Delete
    fun delete(issue: Issue)

    @Query("DELETE FROM Issue")
    fun deleteAll()
}