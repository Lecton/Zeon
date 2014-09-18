/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import communication.Connection;
import javax.swing.JFrame;
import messages.Message;
import communication.handlers.LoginHandler;
import communication.handlers.MessageFactory;
import communication.handlers.MessageHandler;
import mvc.model.ColleagueList;
import mvc.model.Model;
import mvc.view.authentication.Login;
import mvc.view.generalUI.GUI;

/**
 *
 * @author Bernhard
 */
public class Control {
    protected static Control INSTANCE;
    private static MessageHandler msgHandler =new LoginHandler();
    
//    private static ArrayList<
    
    private Connection con;
    private JFrame window;
    
    private GUI ui;
    private Login login;
    
//    private Control() {setup();}
    
    public Control(String HOST, int PORT) throws InterruptedException {
        setup();
        con =new Connection(HOST, PORT);
        con.makeConnection();
    }
    
    private void setup() {
        login =new Login();
        LoginControl.register(login);


        ui =new GUI();
        GUIControl.register(ui);
        ContactListControl.register(ui.getContactList());
        UserControl.register(ui.getUserPanel());
    }
    
    public void close() {
        if (UserControl.hasUser()) {
            con.writeMessage(MessageFactory.generateLogout(UserControl.getUserID()));
        }
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
                        GUIControl.clear();
                        window =login;
                        msgHandler =new LoginHandler();
                        break;
                    case 1:
        //                window =new Registration();
                        break;
                    case 2:
                        window =ui;
                        msgHandler =new MessageHandler();
                        break;
                }
                if (window != null) {
                    window.pack();
                    window.setVisible(true);
                }
            }
        });
    }
}