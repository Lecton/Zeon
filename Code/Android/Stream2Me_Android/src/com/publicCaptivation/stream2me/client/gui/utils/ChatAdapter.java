package com.publicCaptivation.stream2me.client.gui.utils;

import java.util.List;

import com.publicCaptivation.stream2me.client.ClientHandler;
import com.publicCaptivation.stream2me.client.R;

import android.app.ActionBar.LayoutParams;
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
				holder.header = (RelativeLayout) convertView.findViewById(R.id.header);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.timestamp = (TextView) convertView.findViewById(R.id.timestamp);
				holder.message = (TextView) convertView.findViewById(R.id.message);
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder) convertView.getTag();		
			}

			RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.message_frame);
			
			RelativeLayout.LayoutParams li = new RelativeLayout.LayoutParams(
					holder.icon.getLayoutParams().width, 
					holder.icon.getLayoutParams().height);

			RelativeLayout.LayoutParams lh = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 
					holder.header.getLayoutParams().height);

			RelativeLayout.LayoutParams ln = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, 
					holder.name.getLayoutParams().height);

			RelativeLayout.LayoutParams lt = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, 
					holder.timestamp.getLayoutParams().height);
			
			RelativeLayout.LayoutParams lm = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 
					holder.message.getLayoutParams().height);
			
			SpannableString sName;
			SpannableString sMessage; 
			//Check whether message is mine to show green background and align to right
			if(message.isMine())
			{
				holder.icon.setImageBitmap(ClientHandler.getUser().getImage());
				holder.name.setText(ClientHandler.getUser().getFullname());
				holder.timestamp.setText(message.getTimestamp());
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
			    lh.addRule(RelativeLayout.LEFT_OF, holder.icon.getId());
			    ln.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			    lt.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			    
			    lm.addRule(RelativeLayout.BELOW, holder.header.getId());
			    lm.addRule(RelativeLayout.LEFT_OF, holder.icon.getId());	
			    
			    layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
			}
			else
			{
				try {
				//If not mine then it is from sender to show orange background and align to left
					holder.icon.setImageBitmap(ClientHandler.getFromUserID(message.getUserID()).getImage());
					holder.name.setText(ClientHandler.getFromUserID(message.getUserID()).getFullname());
				} catch (Exception ex) {
//					holder.icon.setImageBitmap(ClientHandler.getBLANK());
//					holder.name.setText("User");
				}
				holder.timestamp.setText(message.getTimestamp());
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
			    lh.addRule(RelativeLayout.RIGHT_OF, holder.icon.getId());
			    ln.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			    lt.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			    
			    lm.addRule(RelativeLayout.BELOW, holder.header.getId());
			    lm.addRule(RelativeLayout.RIGHT_OF, holder.icon.getId());
			    layout.setBackgroundColor(Color.parseColor("#FFEBCD"));
			}
			holder.icon.setLayoutParams(li);
			holder.header.setLayoutParams(lh);
			holder.name.setLayoutParams(ln);
			holder.timestamp.setLayoutParams(lt);
			holder.message.setLayoutParams(lm);
//				holder.message.setTextColor(R.color.textColor);
			
			return convertView;
		}
		
		private static class ViewHolder
		{
			ImageView icon;
			RelativeLayout header;
			TextView name;
			TextView timestamp;
			TextView message;
		}

		@Override
		public long getItemId(int position) {
			//Unimplemented, because we aren't using Sqlite.
			return position;
		}

	}
