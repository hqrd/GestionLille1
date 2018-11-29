package com.tac.hqrd.gestionlille1

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.tac.hqrd.gestionlille1.dao.IssueDao
import com.tac.hqrd.gestionlille1.model.Issue

@Database(entities = [Issue::class], version = 1)
abstract class IssuesDatabase : RoomDatabase() {
    abstract fun issueDao(): IssueDao

    companion object {
        private var INSTANCE: IssuesDatabase? = null

        fun getInstance(context: Context): IssuesDatabase? {
            if (INSTANCE == null) {
                synchronized(IssuesDatabase::class) {
                    INSTANCE = databaseBuilder(
                        context.applicationContext,
                        IssuesDatabase::class.java, "issues.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}