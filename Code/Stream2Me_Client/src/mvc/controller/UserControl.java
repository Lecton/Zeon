/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import mvc.model.Model;
import mvc.model.User;
import mvc.view.generalUI.UserPanel;

/**
 *
 * @author Bernhard
 */
public class UserControl {
    protected static UserControl INSTANCE =new UserControl();
    private static UserPanel view;

    public static void register(UserPanel viewUser) {
        view = viewUser;
    }
    
    public String getName() {
        return Model.getUser().getName();
    }
    
    public String getAvatar() {
        return Model.getUser().getAvatar();
    }
    
    public void setName(String name) {
        view.setName(name);
    }
    
    public void setAvatar(String avatar) {
        view.setAvatar(avatar);
    }

    public String getUserID() {
        return Model.getUser().getUserID();
    }
}
