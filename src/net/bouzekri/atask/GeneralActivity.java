package net.bouzekri.atask;

import java.util.ArrayList;
import java.util.List;

import net.bouzekri.atask.helper.TaskDatabaseHelper;
import net.bouzekri.atask.model.Task;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class GeneralActivity extends Activity {

    protected TaskDatabaseHelper taskDBHelper;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    public List<Task> loadNextTasks() {
        List<Task> tasks = new ArrayList<Task>();
        
        SQLiteDatabase sqliteDatabase = getTaskDBHelper().getReadableDatabase();
        Cursor cursor = sqliteDatabase.query(TaskDatabaseHelper.TABLE_NAME, 
                                             new String[] {TaskDatabaseHelper.COLUMN_ID,TaskDatabaseHelper.COLUMN_TITLE, TaskDatabaseHelper.COLUMN_CONTENT}, 
                                             null, null, null, null, null);
        if(cursor.moveToFirst())
        {
            Task newTask;
            do
            {
                newTask = new Task();
                newTask.setId(cursor.getInt(0));
                newTask.setTitle(cursor.getString(1));
                newTask.setContent(cursor.getString(2));
                tasks.add(newTask);
            } while (cursor.moveToNext());
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
    	
    	return tasks;
    }
    
    public TaskDatabaseHelper getTaskDBHelper() {
        return taskDBHelper;
    }
	
    public void setTaskDBHelper(TaskDatabaseHelper taskDBHelper) {
        this.taskDBHelper = taskDBHelper;
    }
}
