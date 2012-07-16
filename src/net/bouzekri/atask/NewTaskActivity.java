package net.bouzekri.atask;

import net.bouzekri.atask.helper.TaskDatabaseHelper;
import net.bouzekri.atask.model.Task;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewTaskActivity extends Activity {

	private TaskDatabaseHelper taskDBHelper;
	private Task currentTask;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setTaskDBHelper(new TaskDatabaseHelper(this));
        
        setContentView(R.layout.new_task);
        
        Intent intent = getIntent();
        currentTask = (Task) intent.getSerializableExtra("Task");
        
        if (currentTask != null) {
            TextView titleInput = (TextView) findViewById(R.id.new_task_form_title_input);
            titleInput.setText(currentTask.getTitle());
            
            TextView contentInput = (TextView) findViewById(R.id.new_task_form_text_input);
            contentInput.setText(currentTask.getContent());
        }
    }
    
    public void cancelAction(View v) {
        finish();
    }
    
    public void saveAction(View v) {
        SQLiteDatabase sqliteDatabase = getTaskDBHelper().getWritableDatabase();
 
        ContentValues contentValues = new ContentValues();

        EditText formTitle = (EditText)findViewById(R.id.new_task_form_title_input);
        EditText formContent = (EditText)findViewById(R.id.new_task_form_text_input);
        
		contentValues.put(TaskDatabaseHelper.COLUMN_TITLE, formTitle.getText().toString());
        contentValues.put(TaskDatabaseHelper.COLUMN_CONTENT, formContent.getText().toString());

        long affectedColumnId;
        if (currentTask == null) {
            affectedColumnId = sqliteDatabase.insert(TaskDatabaseHelper.TABLE_NAME, null, contentValues);
        } else {
            affectedColumnId = sqliteDatabase.update(TaskDatabaseHelper.TABLE_NAME, contentValues, TaskDatabaseHelper.COLUMN_ID + "=?", new String[]{Integer.toString(currentTask.getId())});
        }
        
        sqliteDatabase.close();
        
        Intent saveActionResult = new Intent();
        saveActionResult.putExtra("affectedColumnId", affectedColumnId);
        setResult(RESULT_OK, saveActionResult);
        finish();
    }

    public TaskDatabaseHelper getTaskDBHelper() {
        return taskDBHelper;
    }

    public void setTaskDBHelper(TaskDatabaseHelper taskDBHelper) {
        this.taskDBHelper = taskDBHelper;
    }
}
