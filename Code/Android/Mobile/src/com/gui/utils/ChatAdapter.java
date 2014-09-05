package com.gui.utils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobile.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.Gravity;
import android.widget.LinearLayout.LayoutParams;

public class ChatAdapter extends BaseAdapter {
	 Context context;
	 List<ChatMessages> rowItems;

		public ChatAdapter(Context context, List<ChatMessages> messages) {
			super();
			this.context = context;
			this.rowItems = messages;
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
		public View getView(int position, View convertView, ViewGroup parent) {
			ChatMessages message = (ChatMessages) this.getItem(position);

			ViewHolder holder; 
			if(convertView == null)
			{
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false);
				holder.message = (TextView) convertView.findViewById(R.id.message);
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder) convertView.getTag();		
			}
			
			holder.message.setText(message.getMessage());
			LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();		
				//Check whether message is mine to show green background and align to right
				if(message.isMine())
				{
					holder.message.setBackgroundResource(R.drawable.speech_bubble_green);
					lp.gravity = Gravity.RIGHT;
				}
				//If not mine then it is from sender to show orange background and align to left
				else
				{
					holder.message.setBackgroundResource(R.drawable.speech_bubble_orange);
					lp.gravity = Gravity.LEFT;
				}
				holder.message.setLayoutParams(lp);
//				holder.message.setTextColor(R.color.textColor);
			
			return convertView;
		}
		
		private static class ViewHolder
		{
			TextView message;
		}

		@Override
		public long getItemId(int position) {
			//Unimplemented, because we aren't using Sqlite.
			return position;
		}

	}
