package com.publicCaptivation.stream2me.client.gui.utils;

import java.util.List;

import com.publicCaptivation.stream2me.client.ClientHandler;
import com.publicCaptivation.stream2me.client.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
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
				holder.icon = (ImageView) convertView.findViewById(R.id.icon);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.message = (TextView) convertView.findViewById(R.id.message);
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder) convertView.getTag();		
			}

			RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.message_frame);
			RelativeLayout.LayoutParams li = new RelativeLayout.LayoutParams(
		            							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				RelativeLayout.LayoutParams ln = new RelativeLayout.LayoutParams(
												LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				RelativeLayout.LayoutParams lm = new RelativeLayout.LayoutParams(
												LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			
				SpannableString sName;
				SpannableString sMessage; 
				//Check whether message is mine to show green background and align to right
				if(message.isMine())
				{
					holder.icon.setImageBitmap(ClientHandler.getResizedBitmap(ClientHandler.getUser().getImage(), 150, 150));
					holder.name.setText(ClientHandler.getUser().getName());
					holder.message.setText(message.getMessage());
					
					sName = new SpannableString(holder.name.getText());
					sMessage = new SpannableString(holder.message.getText());
					sName.setSpan(new RelativeSizeSpan(1.0f), 0, sName.length(), 0);
					sMessage.setSpan(new RelativeSizeSpan(1.5f), 0, sMessage.length(), 0);
					sName.setSpan(new StyleSpan(Typeface.BOLD), 0, sName.length(), 0);
					sMessage.setSpan(new StyleSpan(Typeface.ITALIC), 0, sMessage.length(), 0);
					
					holder.name.setText(sName, BufferType.SPANNABLE);
					holder.message.setText(sMessage, BufferType.SPANNABLE);
					
				    li.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//				    ln.addRule(RelativeLayout.LEFT_OF,holder.icon.getId());		
//				    lm.addRule(RelativeLayout.LEFT_OF,holder.icon.getId());	
				    ln.addRule(RelativeLayout.ALIGN_PARENT_LEFT);	
				    lm.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				    lm.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				    layout.setBackgroundColor(Color.parseColor("#FFFFFF"));	
				}
				else
				{
					//If not mine then it is from sender to show orange background and align to left
					holder.icon.setImageBitmap(ClientHandler.getFromUserID(message.getUserID()).getImage());
					holder.name.setText(message.getName());
					holder.message.setText(message.getMessage());
					
					sName = new SpannableString(holder.name.getText());
					sMessage = new SpannableString(holder.message.getText());
					
					sName.setSpan(new RelativeSizeSpan(1.0f), 0, sName.length(), 0);
					sMessage.setSpan(new RelativeSizeSpan(1.5f), 0, sMessage.length(), 0);
					sName.setSpan(new StyleSpan(Typeface.BOLD), 0, sName.length(), 0);
					sMessage.setSpan(new StyleSpan(Typeface.ITALIC), 0, sMessage.length(), 0);
					
					holder.name.setText(sName, BufferType.SPANNABLE);
					holder.message.setText(sMessage, BufferType.SPANNABLE);
					
				    li.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				    ln.addRule(RelativeLayout.RIGHT_OF,holder.icon.getId());				
				    lm.addRule(RelativeLayout.RIGHT_OF,holder.icon.getId());	
				    lm.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				    layout.setBackgroundColor(Color.parseColor("#FFEBCD"));
				}
				holder.icon.setLayoutParams(li);
				holder.name.setLayoutParams(ln);
				holder.message.setLayoutParams(lm);
//				holder.message.setTextColor(R.color.textColor);
			
			return convertView;
		}
		
		private static class ViewHolder
		{
			ImageView icon;
			TextView name;
			TextView message;
		}

		@Override
		public long getItemId(int position) {
			//Unimplemented, because we aren't using Sqlite.
			return position;
		}

	}
