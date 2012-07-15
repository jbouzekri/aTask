package net.bouzekri.atask.adapter;

import java.util.List;

import net.bouzekri.atask.R;
import net.bouzekri.atask.R.id;
import net.bouzekri.atask.R.layout;
import net.bouzekri.atask.model.Task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Task> mTasks;
	
	public TaskListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
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
		
		public void setTitle(String title) {
			mTitle.setText(title);
		}
	}
}
