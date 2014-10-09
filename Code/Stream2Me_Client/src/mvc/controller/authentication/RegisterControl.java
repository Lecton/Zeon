/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller.authentication;

import biz.source_code.base64Coder.Base64Coder;
import communication.handlers.MessageFactory;
import java.util.logging.Logger;
import mvc.controller.Control;
import mvc.view.authentication.Register;

/**
 *
 * @author Bernhard
 */
public class RegisterControl {
    private final static Logger LOGGER = Logger.getLogger(RegisterControl.class.getName());
    
    private static Register view;
    
    public static void register(Register regisetView) {
        view =regisetView;
    }
    
    public static void checkUsername(String username) {
        Control.INSTANCE.writeMessage(MessageFactory.generateCheckUsername(username));
    }
    
    public static void usernameCheckResponse(boolean taken, String error) {
        view.setUsernameValid(taken, error);
    }
    
    public static void checkEmail(String email) {
        Control.INSTANCE.writeMessage(MessageFactory.generateCheckEmail(email));
    }
    
    public static void emailCheckResponse(boolean taken, String error) {
        view.setEmailValid(taken, error);
    }

    public static void register(String username, String name, String surname, String email, String pw) {
        Control.INSTANCE.writeMessage(MessageFactory.generateRegistration(username, name, surname, email, Base64Coder.encodeString(pw)));
    }

    public static void registrationResponse(boolean success, boolean additional_validate) {
        view.setResult(success);
        if (success) {
            if (additional_validate) {
                Control.INSTANCE.initiate(3);
            } else {
                Control.INSTANCE.initiate(0);
            }
        }
    }

    public static void back() {
        Control.INSTANCE.initiate(0);
    }
}
