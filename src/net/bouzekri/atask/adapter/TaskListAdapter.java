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
package net.bouzekri.atask.adapter;

import java.util.List;

import net.bouzekri.atask.R;
import net.bouzekri.atask.model.Task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class TaskListAdapter extends BaseAdapter {

	public static final int NORMAL_LIST = 1;
	public static final int SELECT_LIST = 2;
	
	private LayoutInflater mInflater;
	private List<Task> mTasks;
	private int type;
	
	public TaskListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
		this.type = TaskListAdapter.NORMAL_LIST;
	}
	
	public TaskListAdapter(Context context, int type) {
		mInflater = LayoutInflater.from(context);
		this.type = type;
	}
	
	@Override
	public int getCount() {
		return mTasks.size();
	}

	@Override
	public Object getItem(int position) {
		return mTasks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		switch (this.type) {
			case TaskListAdapter.SELECT_LIST:
				convertView = this.getSelectableView(position, convertView, parent);
				break;
				
			default:
				convertView = this.getNormalView(position, convertView, parent);
				break;
		}
		
		return convertView;
	}
	
	public View getSelectableView(int position, View convertView, ViewGroup parent) {
		Task clickedTask = (Task) this.getItem( position );
        AppViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.task_select_row, null);

            holder = new AppViewHolder();
            holder.mTitle = (TextView) convertView.findViewById(R.id.task_select_row_title);
            holder.mCheckbox = (CheckBox) convertView.findViewById(R.id.task_select_row_checkbox);
            holder.mCheckbox.setOnClickListener( new View.OnClickListener() {  
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Task clickedTask = (Task) cb.getTag();
                    clickedTask.setChecked( cb.isChecked() );
		          }  
	        }); 
			convertView.setTag(holder);
		} else {
			holder = (AppViewHolder) convertView.getTag();
		}
        holder.mCheckbox.setTag( clickedTask );
		holder.setTitle(mTasks.get(position).getTitle());
		
		return convertView;
	}
	
	public View getNormalView(int position, View convertView, ViewGroup parent) {
		AppViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.task_row, null);
			
			holder = new AppViewHolder();
			holder.mTitle = (TextView) convertView.findViewById(R.id.task_row_title);
			convertView.setTag(holder);
		} else {
			holder = (AppViewHolder) convertView.getTag();
		}
		holder.setTitle(mTasks.get(position).getTitle());
		
		return convertView;
	}

	public void setListItems(List<Task> list) {
		mTasks = list;
	}
	
	public class AppViewHolder {
		private TextView mTitle;
		private CheckBox mCheckbox;
		
		public void setTitle(String title) {
			mTitle.setText(title);
		}

		public CheckBox getCheckBox() {
			return this.mCheckbox;
		}
	}
}
