/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Messages.UpdateUser;

import Messages.Message;
import Utils.*;
import client.GUI.GUI;
import server.clientConnection;

/**
 *
 * @author Bernhard
 */
public class UpdateAvatar extends Message {
    private String avatar;
    
    public UpdateAvatar(int ID, String avatar) {
        this.ID =ID;
        this.to =-1;
        this.avatar =avatar;
    }

    @Override
    public String getMessage() {
        return this.getClass().getSimpleName()+" - ID: "+ID;
    }

    @Override
    public void handle(GUI userInterface) {
        System.out.println("Avatar updated");
        userInterface.getContactPane().getContactProfile(ID).updateAvatar(ImageUtils.decodeToImage(avatar));
    }

    @Override
    public Message repackage(clientConnection cc) {
        return this;
    }
    
}
