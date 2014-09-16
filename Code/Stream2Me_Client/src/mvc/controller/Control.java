/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import communication.Connection;
import javax.swing.JFrame;
import messages.Message;
import mvc.controller.message.LoginHandler;
import mvc.controller.message.MessageFactory;
import mvc.controller.message.MessageHandler;
import mvc.model.Model;
import mvc.view.authentication.Login;
import mvc.view.generalUI.GUI;

/**
 *
 * @author Bernhard
 */
public class Control {
    protected static Control INSTANCE =new Control();
    private static MessageHandler msgHandler =new LoginHandler();
    
//    private static ArrayList<
    
    private Connection con;
    private JFrame window;
    
    private Control() {}
    
    public Control(String HOST, int PORT) throws InterruptedException {
        con =new Connection(HOST, PORT);
        con.makeConnection();
    }
    
    public void close() {
        con.writeMessage(MessageFactory.generateLogout(Model.INSTANCE.getUserID()));
        con.close();
        UpdateControl.INSTANCE.stop();
    }
    
    public static boolean handleMessage(Message msg) {
        return msgHandler.handle(msg);
    }
    
    protected void writeMessage(Message msg) {
        con.writeMessage(msg);
    }
    
    protected void initiate(final int windowID) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (window != null) {
                    window.dispose();
                    window =null;
                }

                switch (windowID) {
                    case 0:
                        window =new Login();
                        msgHandler =new LoginHandler();
                        LoginControl.register((Login) window);
                        break;
                    case 1:
        //                window =new Registration();
                        break;
                    case 2:
                        window =new GUI();
                        ((GUI)window).setupControls();
                        msgHandler =new MessageHandler();
                        break;
                }
                if (window != null) {
                    window.setVisible(true);
                }
            }
        });
    }
}