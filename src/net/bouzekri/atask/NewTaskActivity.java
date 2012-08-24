/**
 * Copyright 2012 Jonathan Bouzekri <jonathan.bouzekri@gmail.com>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
package net.bouzekri.atask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ptashek.widgets.datetimepicker.DateTimePicker;

import net.bouzekri.atask.helper.TaskDatabaseHelper;
import net.bouzekri.atask.model.Task;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


public class NewTaskActivity extends Activity implements OnClickListener {

	private TaskDatabaseHelper taskDBHelper;
	private Task currentTask;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setTaskDBHelper(new TaskDatabaseHelper(this));
        
        setContentView(R.layout.new_task);
        
        findViewById(R.id.new_task_form_date_time_input).setOnClickListener(this);

        Intent intent = getIntent();
        currentTask = (Task) intent.getSerializableExtra("Task");
        
        if (currentTask != null) {
            TextView titleInput = (TextView) findViewById(R.id.new_task_form_title_input);
            titleInput.setText(currentTask.getTitle());
            
            TextView contentInput = (TextView) findViewById(R.id.new_task_form_text_input);
            contentInput.setText(currentTask.getContent());
            
            TextView dateTimeInput = (TextView) findViewById(R.id.new_task_form_date_time_input);
            dateTimeInput.setText(DateFormat.getDateTimeInstance().format(new Date(currentTask.getDate())));
        } else {
            currentTask = new Task();
        }
    }
    
    public void cancelAction(View v) {
        finish();
    }
    
    public void saveAction(View v) {
        SQLiteDatabase sqliteDatabase = getTaskDBHelper().getWritableDatabase();
 
        ContentValues contentValues = new ContentValues();

        EditText formTitle = (EditText)findViewById(R.id.new_task_form_title_input);
        currentTask.setTitle(formTitle.getText().toString());
        EditText formContent = (EditText)findViewById(R.id.new_task_form_text_input);
        currentTask.setContent(formContent.getText().toString());
        
		contentValues.put(TaskDatabaseHelper.COLUMN_TITLE, currentTask.getTitle());
        contentValues.put(TaskDatabaseHelper.COLUMN_CONTENT, currentTask.getContent());
        contentValues.put(TaskDatabaseHelper.COLUMN_DATE, currentTask.getDate());

        long affectedColumnId;
        if (currentTask.getId() == 0) {
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

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.new_task_form_date_time_input)
        	showDateTimeDialog();
	}
	
	public void showDateTimeDialog() {
		// Create the dialog
        final Dialog mDateTimeDialog = new Dialog(this);
        // Inflate the root layout
        final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater().inflate(R.layout.date_time_dialog, null);
        // Grab widget instance
        final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);
        // Check is system is set to use 24h time (this doesn't seem to work as expected though)
        // final String timeS = android.provider.Settings.System.getString(getContentResolver(), android.provider.Settings.System.TIME_12_24);
        // final boolean is24h = !(timeS == null || timeS.equals("12"));
        final boolean is24h = true;
        
        // Update demo TextViews when the "OK" button is clicked 
        ((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime)).setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                        mDateTimePicker.clearFocus();
                        Long selectedDate = mDateTimePicker.getDateTimeMillis();
                        Date date = new Date(selectedDate);
                        ((TextView) findViewById(R.id.new_task_form_date_time_input)).setText(DateFormat.getDateTimeInstance().format(date));
                        currentTask.setDate(selectedDate);
                        mDateTimeDialog.dismiss();
                }
        });

        // Cancel the dialog when the "Cancel" button is clicked
        ((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog)).setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                        mDateTimeDialog.cancel();
                }
        });

        // Reset Date and Time pickers when the "Reset" button is clicked
        ((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime)).setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                        mDateTimePicker.reset();
                }
        });
        
        // Setup TimePicker
        mDateTimePicker.setIs24HourView(is24h);
        // No title on the dialog window
        mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Set the dialog content view
        mDateTimeDialog.setContentView(mDateTimeDialogView);
        
        if (currentTask != null && currentTask.getDate() > 0) {
            Calendar taskCalendar = Calendar.getInstance();
            taskCalendar.setTimeInMillis(currentTask.getDate());
            mDateTimePicker.updateDate(taskCalendar.get(Calendar.YEAR), taskCalendar.get(Calendar.MONTH), taskCalendar.get(Calendar.DAY_OF_MONTH));
            if (is24h) {
                mDateTimePicker.updateTime(taskCalendar.get(Calendar.HOUR_OF_DAY), taskCalendar.get(Calendar.MINUTE));
            } else {
            	mDateTimePicker.updateTime(taskCalendar.get(Calendar.HOUR), taskCalendar.get(Calendar.MINUTE));
            }
        }
        
        // Display the dialog
        mDateTimeDialog.show();

	}
}
