/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database;

import core.database.online.OnlineDatabase;
import core.database.online.OnlineRegistrationHandler;
import core.database.online.OnlineSettingHandler;
import core.database.online.OnlineStreamHandler;
import core.database.online.OnlineStringMessageHandler;
import core.database.online.OnlineUserHandler;

/**
 *
 * @author Bernhard
 */
public class DatabaseHandler {
    public static OnlineRegistrationHandler registrationHandler;
    public static OnlineSettingHandler settingHandler;
    public static OnlineStreamHandler streamHandler;
    public static OnlineStringMessageHandler stringMessageHandler;
    public static OnlineUserHandler userHandler;
    public static OnlineDatabase database;

    public static boolean setOffline() {
//        registrationHandler =new OnlineRegistrationHandler();
//        settingHandler =new OnlineSettingHandler();
//        streamHandler =new OnlineStreamHandler();
//        stringMessageHandler =new OnlineStringMessageHandler();
//        userHandler =new OnlineUserHandler();
        return false;
    }

    public static boolean setOnline() {
        registrationHandler =new OnlineRegistrationHandler();
        settingHandler =new OnlineSettingHandler();
        streamHandler =new OnlineStreamHandler();
        stringMessageHandler =new OnlineStringMessageHandler();
        userHandler =new OnlineUserHandler();
        database =new OnlineDatabase();
        
        return database.connect();
    }
}
