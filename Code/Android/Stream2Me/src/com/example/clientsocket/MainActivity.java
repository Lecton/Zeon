package com.example.clientsocket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
TextView serverMessage;
Thread m_objThreadClient;
Connection con;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        serverMessage=(TextView)findViewById(R.id.textView1);
    }
    
public void Start(View view)
{
	m_objThreadClient=new Thread(new Runnable() {
		  public void run()
	       {
	          try 
	           {
	        	  con = new Connection();
	        	  con.setAddress("192.168.216.1");
	        	  con.setPORT(2014);
	        	  con.makeConnection();
	        	  con.write("Hellow there");
			   } 
	           catch (Exception e)
	           {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
	         }
			});
	 
	 m_objThreadClient.start();

}
   
}
