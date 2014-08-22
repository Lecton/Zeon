package com.gui.utils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.activitytut.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter {
	 Context context;
	 List<ChatMessages> rowItems;

	 public ChatAdapter(Context context, List<ChatMessages> rowItems) {
		  this.context = context;
		  this.rowItems = rowItems;
	 }

	 @Override
	 public int getCount() {
	  return rowItems.size();
	 }

	 @Override
	 public Object getItem(int position) {
	  return rowItems.get(position);
	 }

	 @Override
	 public long getItemId(int position) {
	  return rowItems.indexOf(getItem(position));
	 }

	 public List<ChatMessages> getRowItems() {
		return rowItems;
	}
	 
	 /* private view holder class */
	 private class ViewHolder {
		  TextView name;
		  TextView message;
		  TextView timestamp;
	 }

	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder = null;

	  LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	  
	  if (convertView == null) 
	  { 
		   convertView = mInflater.inflate(R.layout.chat_list, null);
		   holder = new ViewHolder();
	
		   holder.name = (TextView) convertView.findViewById(R.id.name);
		   holder.message = (TextView) convertView.findViewById(R.id.message);
		   holder.timestamp = (TextView) convertView.findViewById(R.id.timestamp);
	
		   ChatMessages row_pos = rowItems.get(position);

		   holder.name.setText(row_pos.getName());
		   holder.message.setText(row_pos.getMessage());
		   holder.timestamp.setText(row_pos.getTimestamp());
	
		   convertView.setTag(holder);
	  } 
	  else
	  {
		  holder = (ViewHolder) convertView.getTag();
	  }

	  return convertView;
	 }
	 
	}