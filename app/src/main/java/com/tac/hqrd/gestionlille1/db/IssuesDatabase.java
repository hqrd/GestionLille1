package com.tac.hqrd.gestionlille1.db;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.tac.hqrd.gestionlille1.db.dao.IssueDao;
import com.tac.hqrd.gestionlille1.db.entity.Issue;

@Database(entities = {Issue.class}, version = 1)
public abstract class IssuesDatabase extends RoomDatabase {

    public abstract IssueDao issueDao();

    private static IssuesDatabase INSTANCE;

    public static IssuesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (IssuesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            IssuesDatabase.class, "issue_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}