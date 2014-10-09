/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database;

import core.database.online.Database;
import core.database.online.RegistrationHandler;
import core.database.online.SettingHandler;
import core.database.online.StreamHandler;
import core.database.online.StringMessageHandler;
import core.database.online.UserHandler;

/**
 *
 * @author Bernhard
 */
public class DatabaseHandler {
    public static RegistrationHandler registrationHandler;
    public static SettingHandler settingHandler;
    public static StreamHandler streamHandler;
    public static StringMessageHandler stringMessageHandler;
    public static UserHandler userHandler;

    public static void setOffline() {
        
    }

    public static boolean setOnline() {
        registrationHandler =new RegistrationHandler();
        settingHandler =new SettingHandler();
        streamHandler =new StreamHandler();
        stringMessageHandler =new StringMessageHandler();
        userHandler =new UserHandler();
        
        return Database.INSTANCE.connect();
    }
}
