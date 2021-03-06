package com.example.mylist;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {

 String[] member_names ;//= {"Lecton","Bernhard","Zenadia","Denvin","Lucky","Ivan"};
 TypedArray profile_pics;
 String[] statues;
 String[] contactType;

 List<RowItem> rowItems;
 ListView mylistview;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.collen);

  rowItems = new ArrayList<RowItem>();
//
  member_names = getResources().getStringArray(R.array.Member_names);
//
  profile_pics = getResources().obtainTypedArray(R.array.profile_pics);
//  
  statues = getResources().getStringArray(R.array.statues);
//
  contactType = getResources().getStringArray(R.array.contactType);

  
  for (int i = 0; i < member_names.length; i++)
  {
	  	 RowItem item = new RowItem(member_names[i],
	     profile_pics.getResourceId(i, -1), statues[i],
	     contactType[i]);
	  	 rowItems.add(item);
  }

  mylistview = (ListView) findViewById(R.id.contact);
  ClientAdapter adapter = new ClientAdapter(this, rowItems);
  mylistview.setAdapter(adapter);
  profile_pics.recycle();
  mylistview.setOnItemClickListener(this);

 }

 @Override
 public void onItemClick(AdapterView<?> parent, View view, int position,
   long id) {

  String member_name = rowItems.get(position).getMember_name();
  Toast.makeText(getApplicationContext(), "" + member_name,
    Toast.LENGTH_SHORT).show();
 }

}
