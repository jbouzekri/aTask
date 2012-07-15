package net.bouzekri.atask;

import java.util.ArrayList;
import java.util.List;

import net.bouzekri.atask.adapter.TaskListAdapter;
import net.bouzekri.atask.helper.TaskDatabaseHelper;
import net.bouzekri.atask.model.Task;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class ATaskActivity extends Activity {

	public static final int TASK_FORM = 1;
	
	private ListView mTasksList;
	private List<Task> mTasks;
	
	private TaskDatabaseHelper taskDBHelper;
	private TaskListAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setTaskDBHelper(new TaskDatabaseHelper(this));
        
        setContentView(R.layout.main);
        
        mTasksList = (ListView) findViewById(R.id.next_task_list);
        mTasksList.setEmptyView(findViewById(R.id.no_task));
        mTasks = loadNextTasks();
        
        adapter = new TaskListAdapter(getApplicationContext());
        adapter.setListItems(mTasks);
        mTasksList.setAdapter(adapter);
    }

    @Override
    public void onResume() {
    	super.onResume();
    	mTasks = loadNextTasks();
    	adapter.setListItems(mTasks);
    	adapter.notifyDataSetChanged();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_task:
                Intent newTask = new Intent(this, NewTaskActivity.class);
                startActivityForResult(newTask, TASK_FORM);
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        switch (requestCode) {
            case TASK_FORM:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, getString(R.string.canceled_task), Toast.LENGTH_SHORT).show();
                } else {
                	Toast.makeText(this, getString(R.string.saved_task)+" "+data.getLongExtra("affectedColumnId", 0), Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
    }
    
    public List<Task> loadNextTasks() {
    	List<Task> tasks = new ArrayList<Task>();
    	
    	SQLiteDatabase sqliteDatabase = getTaskDBHelper().getReadableDatabase();
    	Cursor cursor = sqliteDatabase.query(TaskDatabaseHelper.TASK_TABLE_NAME, 
    										 new String[] {TaskDatabaseHelper.TASK_TABLE_TITLE, TaskDatabaseHelper.TASK_TABLE_CONTENT}, 
										 	null, null, null, null, null);
    	if(cursor.moveToFirst())
        {
    		Task newTask;
            do
            {
            	newTask = new Task();
                newTask.setTitle(cursor.getString(0));
                newTask.setContent(cursor.getString(1));
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
