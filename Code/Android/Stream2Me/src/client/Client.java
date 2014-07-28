package client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import client.GUI.GUI;

import com.example.clientsocket.R;

public class Client extends Activity {
	public String tvName;
	public String tvAddress;
	public int tvPort;
	public GUI userInstance = null;
	public Client client = null;



	public Client(){}   
	
	public Client(String name, String address,int PORT) {
        this.tvPort =PORT;
        this.tvName =name;	
        this.tvAddress =address;		
		
	}
	
	public void Start(View view)
	{

	}
	
	public void init(){	
		client = new Client("a","192.168.43.252",2014);
		userInstance = new GUI(client);
//		userInstance.connect();		
	}
 
	/////////////////////////////////////////////////////////////////
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.fragment_main);

	    init();
	}	
}
