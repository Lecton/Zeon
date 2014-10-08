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
import communication.handlers.RegistrationHandler;
import communication.handlers.SettingsHandler;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.view.authentication.Login;
import mvc.view.authentication.Register;
import mvc.view.generalUI.GUI;
import mvc.view.generalUI.Settings;

/**
 *
 * @author Bernhard
 */
public class Control {
    private final static Logger LOGGER = Logger.getLogger(Control.class.getName());
    
    protected static Control INSTANCE;
    private static MessageHandler msgHandler =new LoginHandler();
    
//    private static ArrayList<
    
    private Connection con;
    private JFrame window;
    
    private GUI ui;
    private Login login;
    private Register register;
    private Settings settings;
    
    public Control(String HOST, int PORT) throws InterruptedException {
        setup();
        con =new Connection(HOST, PORT);
        con.makeConnection();
    }
    
    private void setup() {
        setupLogin();
        
        setupRegister();
        
        setupGUI();
        
        setupSettings();
    }
    
    private void setupLogin() {
        login =new Login();
        LoginControl.register(login);
    }
    
    private void setupRegister() {
        register =new Register();
        RegisterControl.register(register);
    }
    
    private void setupGUI() {
        ui =new GUI();
        GUIControl.register(ui);
        ContactListControl.register(ui.getContactList());
        UserControl.register(ui.getUserPanel());
        ChatControl.register(ui.getMessagePanel());
        ProfileControl.register(ui.getProfilePanel());
    }
    
    private void setupSettings() {
        settings =new Settings();
        SettingsControl.register(settings);
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
                        login.clear();
                        window =login;
                        msgHandler =new LoginHandler();
                        break;
                    case 1:
                        login.clear();
                        GUIControl.clear();
                        window =register;
                        msgHandler =new RegistrationHandler();
                        break;
                    case 2:
                        login.clear();
                        GUIControl.changeContent(3, null, UserControl.getUserID());
                        window =ui;
                        GUIControl.requestUpdateList();
                        msgHandler =new MessageHandler();
                        break;
                    case 3:
                        msgHandler =new SettingsHandler();
                        SettingsControl.open(0);
                        window =settings;
                        break;
                    case 4: //Settings without save
                        window =ui;
                        List<Message> pool =msgHandler.getPrivatePool();
                        msgHandler =new MessageHandler();
                        msgHandler.empty(pool);
                        break;
                    case 5:
                        login.clear();
                        GUIControl.reset();
                        window =ui;
                        GUIControl.requestUpdateList();
                        msgHandler =new RegistrationHandler();
                        break;
                        
                }
                if (window != null) {
                    window.pack();
                    window.setVisible(true);
                } else {
                    LOGGER.log(Level.SEVERE, "Unknown open window. Exiting");
                    System.exit(1);
                }
            }
        });
    }
}