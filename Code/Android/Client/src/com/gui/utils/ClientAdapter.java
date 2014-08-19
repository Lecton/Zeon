package com.gui.utils;
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

public class ClientAdapter extends BaseAdapter {
	 Context context;
	 List<Contact> rowItems;

	 public ClientAdapter(Context context, List<Contact> rowItems) {
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

	 public List<Contact> getRowItems() {
		return rowItems;
	}
	 
	 /* private view holder class */
	 private class ViewHolder {
		  ImageView profile_pic;
		  TextView cName;
		  TextView cEmail;
		  private int ID;
	 }

	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder = null;

	  LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	  
	  if (convertView == null) 
	  {
		   convertView = mInflater.inflate(R.layout.list_item, null);
		   holder = new ViewHolder();
	
		   holder.cName = (TextView) convertView.findViewById(R.id.member_name);
		   holder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_pic);
	
		   Contact row_pos = rowItems.get(position);
	
		   holder.profile_pic.setImageBitmap(row_pos.getImage());
		   holder.cName.setText(row_pos.getName());
//		   holder.cEmail.setText(row_pos.getEmail());
	
		   convertView.setTag(holder);
	  } 
	  else
	  {
		  holder = (ViewHolder) convertView.getTag();
	  }

	  return convertView;
	 }
	 
	}