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

import java.util.ArrayList;
import java.util.List;

import net.bouzekri.atask.adapter.TaskListAdapter;
import net.bouzekri.atask.adapter.TaskListAdapter.AppViewHolder;
import net.bouzekri.atask.helper.TaskDatabaseHelper;
import net.bouzekri.atask.model.Task;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SelectTaskActivity extends GeneralActivity implements OnItemClickListener {

    private ListView selectTasksList;
    private List<Task> mTasks;
    
    private TaskListAdapter adapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setTaskDBHelper(new TaskDatabaseHelper(this));
        
        setContentView(R.layout.select);
        
        selectTasksList = (ListView) findViewById(R.id.select_task_list);
        selectTasksList.setOnItemClickListener(this);
        mTasks = loadNextTasks();
        
        adapter = new TaskListAdapter(getApplicationContext(), TaskListAdapter.SELECT_LIST);
        adapter.setListItems(mTasks);
        selectTasksList.setAdapter(adapter);
    }
    
    public void deleteSelectedTasks(View v) {
        List<Task> remainingTasks = new ArrayList<Task>();
        for (Task task : mTasks) {
            if (task.isChecked()) {
                getTaskDBHelper().getWritableDatabase().delete(TaskDatabaseHelper.TABLE_NAME, TaskDatabaseHelper.COLUMN_ID+"=?", new String[] { Integer.toString(task.getId()) });
            } else {
                remainingTasks.add(task);
            }
        }
        mTasks = remainingTasks;
        adapter.setListItems(mTasks);
        adapter.notifyDataSetChanged();
    }
    
    public void doneSelectedTasks(View v) {
    	
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
        Task selectedTask = (Task) adapter.getItem( position );  
        selectedTask.toggleChecked();
        AppViewHolder viewHolder = (AppViewHolder) item.getTag();  
        viewHolder.getCheckBox().setChecked( selectedTask.isChecked() );  
	}
}
