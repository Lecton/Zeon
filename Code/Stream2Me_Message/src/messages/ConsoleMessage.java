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
    String serverName;

    public ConsoleMessage(String message, String serverName) {
        this.message = message;
        this.serverName =serverName;
    }
    
    @Override
    public String getMessage() {
        return "["+serverName.toUpperCase()+"]: "+message;
    }

    @Override
    public MessageType handle() {
        return MessageType.console;
    }

    public String getServerName() {
        return serverName;
    }
}
