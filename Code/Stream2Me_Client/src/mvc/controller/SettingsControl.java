/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import communication.handlers.MessageFactory;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.settings.group.GroupJoinMessage;

/**
 *
 * @author Bernhard
 */
public class SettingsControl {
    private final static Logger LOGGER = Logger.getLogger(SettingsControl.class.getName());
    
    public static final SettingsControl INSTANCE =new SettingsControl();
    
    private static mvc.model.Settings model;
    private static mvc.view.generalUI.Settings view;
    
    protected static void register(mvc.view.generalUI.Settings settingsView) {
        view =settingsView;
    }

    public static void invalidRequest() {
        INSTANCE.initiate(0);
    }

    public static String getUserFullname() {
        return UserControl.INSTANCE.getFullname();
    }

    public void requestGroupList() {
        Control.INSTANCE.writeMessage(MessageFactory.generateSettingsGroupListRequest(UserControl.getUserID()));
    }
    
    public void initiate(int target) {
        switch (target) {
            case 0: //exit
                model =null;
                Control.INSTANCE.initiate(4);
                break;
            case 1: //save
                
                break;
            default:
                LOGGER.log(Level.SEVERE, "Unknown initiate Command");
        }
    }
    
    protected static void open(int state) {
        model =new mvc.model.Settings();
        view.clear();
        view.setView(state);
        view.pack();
        Control.INSTANCE.writeMessage(
                MessageFactory.generateSettingsRequestMessage(
                        UserControl.getUserID()));
    }
    
    public static boolean handleSettingsMessage(String groupID, String groupName, String ownerName, boolean owner) {
        if (model != null) {
            model.setGroupID(groupID);
            model.setGroupName(groupName);
            model.setOwner(owner);
            if (ownerName == null) {
                model.setOwnerName("Stream2Me SA");
            } else {
                model.setOwnerName(ownerName);
            }
            view.updateDetails(model.getGroupName(), model.getOwnerName(), model.isOwner());
            return true;
        }
        return false;
    }
    
    public static boolean handleGroupListMessage(Map<String, String> groups) {
        if (model != null) {
            model.setGroups(groups);
            view.updateGroupList(groups.values());
            return true;
        }
        return false;
    }

    public static boolean handleGroupJoinMessage(boolean success, String error) {
        if (model != null) {
            if (success) {
                view.setJoinResponse("Successful.");
                Control.INSTANCE.initiate(5);
            } else {
                view.setJoinResponse(error);
            }
            return true;
        }
        return false;
    }
    
    public static boolean handleGroupCreateMessage(boolean success, String error) {
        if (model != null) {
            if (success) {
                view.setCreateResponse("Successful.");
                Control.INSTANCE.initiate(5);
            } else {
                view.setCreateResponse(error);
            }
            return true;
        }
        return false;
    }

    public void leave() {
        Control.INSTANCE.initiate(4);
    }

    public void joinGroup(String groupName, int index, String password) {
        String groupID =model.getGroupListID(index, groupName);
        if (groupID != null) {
            Control.INSTANCE.writeMessage(MessageFactory.generateSettingsGroupJoin(groupID, UserControl.getUserID(), password));
        } else {
            System.out.println("Group not found");
        }
    }

    public void createGroup(String groupName, String password) {
        Control.INSTANCE.writeMessage(MessageFactory.generateSettingsCreateGroup(UserControl.getUserID(), groupName, password));
    }
    
    public void clear() {
        model.clear();
        view.clear();
    }
}
