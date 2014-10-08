/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import messages.Message;
import messages.settings.group.GroupJoinMessage;
import messages.settings.group.GroupListMessage;
import messages.settings.SettingsMessage;
import messages.settings.group.GroupCreateMessage;
import mvc.controller.SettingsControl;

/**
 *
 * @author Bernhard
 */
public class SettingsHandler extends MessageHandler {
    private final static Logger LOGGER = Logger.getLogger(SettingsHandler.class.getName()); 
    List<Message> pool;

    public SettingsHandler() {
        pool =new ArrayList<>();
    }
    
    @Override
    public boolean handle(Message msg) {
        switch (msg.handle()) {
            case settings:
                SettingsMessage sm =(SettingsMessage)msg;
                if (sm.isEmpty()) {
                    SettingsControl.invalidRequest();
                } else {
                    return SettingsControl.handleSettingsMessage(sm.getGroupID(), sm.getGroupName(), sm.getOwnerName(), sm.isOwner());
                }
                return true;
            case settingsGroupList:
                GroupListMessage glm =(GroupListMessage)msg;
                return SettingsControl.handleGroupListMessage(glm.getGroups());
            case settingsGroupJoin:
                GroupJoinMessage gjm =(GroupJoinMessage)msg;
                return SettingsControl.handleGroupJoinMessage(gjm.isSuccessful(), gjm.getError());
            case settingsGroupCreate:
                GroupCreateMessage gcm =(GroupCreateMessage)msg;
                return SettingsControl.handleGroupCreateMessage(gcm.isSuccessful(), gcm.getError());
            case console:
                return handleConsole(msg);
            default:
                pool.add(msg);
                return true;
        }
    }

    @Override
    public List<Message> getPrivatePool() {
        return pool;
    }
}