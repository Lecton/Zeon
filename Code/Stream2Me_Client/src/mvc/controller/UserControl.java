/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import mvc.view.generalUI.UserPanel;

/**
 *
 * @author Bernhard
 */
public class UserControl {
    public static UserControl INSTANCE =new UserControl();
    private static UserPanel view;
    
    public static void register(UserPanel user) {
        view =user;
    }

    public static boolean unregistered() {
        return view == null;
    }
    
    public void setName(String name) {
        view.setName(name);
    }
    
    public void setAvatar(String avatar) {
        view.setAvatar(avatar);
    }
}
