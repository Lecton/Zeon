/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import biz.source_code.base64Coder.Base64Coder;
import communication.handlers.MessageFactory;
import java.util.logging.Logger;
import messages.userConnection.GreetingMessage;
import mvc.view.authentication.Login;

/**
 *
 * @author Bernhard
 */
public class LoginControl {
    private final static Logger LOGGER = Logger.getLogger(LoginControl.class.getName());
    
    private static Login view;
    
    public static void register(Login loginView) {
        view =loginView;
    }
    
    public static void login(String username, char[] password) {
        String pwd =Base64Coder.encodeString(new String(password));
        Control.INSTANCE.writeMessage(MessageFactory.generateLogin(username, pwd));
//        if (con != null) {
//            try {
//                con.write(MessageFactory.generateLogin(txtUsername.getText(), "pass123"));
//            } catch (IOException ex) {
//                lblResponse.setText("Connection error");
//                Log.error(this.getClass(), "Connection error");
//            }
//        }
    }
    
    public static void response(boolean result, String response, GreetingMessage msg) {
        view.setResponse(response);
        if (result) {
            String userID =msg.getUserID();
            String username =msg.getUsername();
            String name =msg.getName();
            String surname =msg.getSurname();
            String email =msg.getEmail();
            String avatar =msg.getAvatar();
            String title =msg.getTitle();
            String aboutMe =msg.getAboutMe();

            UserControl.setUser(userID, username, name, surname, email, avatar, title, aboutMe);
            StreamControl.register(userID);
            MessageControl.clear();
            view.dispose();
            Control.INSTANCE.initiate(2);
            UpdateControl.INSTANCE.add(userID, UpdateControl.LOGIN);
        }
    }
    
    public static void viewRegistration() {
        view.dispose();
        Control.INSTANCE.initiate(1);
    }
    
    public static void updateList() {
//        String pwd =new String(password);
//        Control.INSTANCE.writeMessage(MessageFactory.generateLogin(username, pwd));
//        if (con != null) {
//            try {
//                con.write(MessageFactory.generateLogin(txtUsername.getText(), "pass123"));
//            } catch (IOException ex) {
//                lblResponse.setText("Connection error");
//                Log.error(this.getClass(), "Connection error");
//            }
//        }
    }
}
