package net.bouzekri.atask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDatabaseHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 2;
	
	private static final String DATABASE_NAME = "atask";
	
    public static final String TASK_TABLE_NAME = "task";
    public static final String TASK_TABLE_TITLE = "title";
    public static final String TASK_TABLE_CONTENT = "content";
    
    private static final String TASK_TABLE_CREATE =
                "CREATE TABLE IF NOT EXISTS " + TASK_TABLE_NAME + " (" +
                TASK_TABLE_TITLE + " TEXT, " +
                TASK_TABLE_CONTENT + " TEXT);";

    TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASK_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
