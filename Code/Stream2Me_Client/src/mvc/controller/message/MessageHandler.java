/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller.message;

import messages.Message;

/**
 *
 * @author Bernhard
 */
public class MessageHandler {
    public boolean handle(Message msg) {
        switch (msg.handle()) {
            case console:
                return handleConsole(msg);
            default:
                return false;
        }
    }
    
    boolean handleConsole(Message msg) {
        System.out.println(msg.getMessage());
        return true;
    }
}