/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messages;

/**
 *
 * @author Bernhard
 */
public class ConsoleMessage extends Message {
    String message;

    public ConsoleMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return "[SERVER]: "+message;
    }

    @Override
    public MessageType handle() {
        return MessageType.console;
    }
    
}
