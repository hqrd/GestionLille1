package com.tac.hqrd.gestionlille1.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tac.hqrd.gestionlille1.db.dao.IssueDao
import com.tac.hqrd.gestionlille1.db.entity.Issue

@Database(entities = [Issue::class], version = 1)
@TypeConverters(IssueTypeConverter::class)
abstract class IssuesDatabase : RoomDatabase() {

    abstract fun issueDao(): IssueDao

    companion object {

        private var INSTANCE: IssuesDatabase? = null

        fun getDatabase(context: Context): IssuesDatabase {
            if (INSTANCE == null) {
                synchronized(IssuesDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            IssuesDatabase::class.java, "issue_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }


}