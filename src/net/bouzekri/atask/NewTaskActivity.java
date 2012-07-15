package net.bouzekri.atask;

import net.bouzekri.atask.helper.TaskDatabaseHelper;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewTaskActivity extends Activity {

	private TaskDatabaseHelper taskDBHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setTaskDBHelper(new TaskDatabaseHelper(this));
        
        setContentView(R.layout.new_task);
    }
    
    public void cancelAction(View v) {
        finish();
    }
    
    public void saveAction(View v) {
        SQLiteDatabase sqliteDatabase = getTaskDBHelper().getWritableDatabase();
 
        ContentValues contentValues = new ContentValues();

        EditText formTitle = (EditText)findViewById(R.id.new_task_form_title_input);
        EditText formContent = (EditText)findViewById(R.id.new_task_form_text_input);
        
		contentValues.put(TaskDatabaseHelper.TASK_TABLE_TITLE, formTitle.getText().toString());
        contentValues.put(TaskDatabaseHelper.TASK_TABLE_CONTENT, formContent.getText().toString());

        long affectedColumnId = sqliteDatabase.insert(TaskDatabaseHelper.TASK_TABLE_NAME, null, contentValues);
        
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
