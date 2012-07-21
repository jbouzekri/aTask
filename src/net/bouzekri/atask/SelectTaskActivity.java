package net.bouzekri.atask;

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
import android.widget.Toast;

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
    	
    }
    
    public void doneSelectedTasks(View v) {
    	
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
		Toast.makeText(this, "clicked "+position, Toast.LENGTH_SHORT).show();
        Task selectedTask = (Task) adapter.getItem( position );  
        selectedTask.toggleChecked();
        AppViewHolder viewHolder = (AppViewHolder) item.getTag();  
        viewHolder.getCheckBox().setChecked( selectedTask.isChecked() );  
	}
}
