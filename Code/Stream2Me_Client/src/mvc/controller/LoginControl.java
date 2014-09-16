/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import mvc.controller.message.MessageFactory;
import mvc.view.authentication.Login;

/**
 *
 * @author Bernhard
 */
public class LoginControl {
    private static Login view;
    
    public static void register(Login loginView) {
        view =loginView;
    }
    
    public static void login(String username, char[] password) {
        String pwd =new String(password);
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
    
    public static void response(boolean result, String response) {
        view.setResponse(response);
        if (result) {
            view.dispose();
            Control.INSTANCE.initiate(2);
        }
    }
}
