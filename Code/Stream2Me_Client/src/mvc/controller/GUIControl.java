/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import mvc.view.generalUI.GUI;

/**
 *
 * @author Bernhard
 */
public class GUIControl {
    private static GUI view;
    
    public static void register(GUI ui) {
        view =ui;
    }
    
    protected static void changeContent(int type, String userID) {
        switch (type) {
            case 0:
                //show profile
                break;
            case 1:
                //show message
                break;
            case 2:
                //show user profile
                break;
            case 3:
                //show group message
                break;
            default:
                //show group message
        }
    }
}
