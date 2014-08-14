package client.GUI;

//import java.io.InputStream;

import android.app.Activity;
import client.Client;
import client.Connection;

import com.example.clientsocket.R;

public class GUI extends Activity{
    
    private Connection con =null;
//    private InputStream input =null;
    private Client client =null;
    
    private String username ="User";
    private int ID =-1;
    Thread connectionThread;
    public GUI(){}
    /**
     * Creates new form GUI
     */
    public GUI(Client client) {

//    setContentView(R.layout.fragment_main);    	
//        this.client = client;
//        this.username =this.client.tvName;
//        con =new Connection();
//        con.setPORT(this.client.tvPort);
//        con.setAddress(this.client.tvAddress);
//
////        setContentView(R.layout.fragment_main);
//        connect();

    } 
    
    public void connect(){

    	connectionThread=new Thread(new Runnable() {
  		  public void run()
  	       {
  	          try 
  	           {

  	        	  con.makeConnection();
  	        	  con.write((""));
  			   } 
  	           catch (Exception e)
  	           {
  					e.printStackTrace();
  				}
  				 
  	         }
  			});
  	 
    	connectionThread.start();    	
    }

}
