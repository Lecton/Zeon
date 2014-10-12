/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database;

import core.database.abstractInterface.Database;
import core.database.abstractInterface.RegistrationHandler;
import core.database.abstractInterface.SettingHandler;
import core.database.abstractInterface.StreamHandler;
import core.database.abstractInterface.StringMessageHandler;
import core.database.abstractInterface.UserHandler;
import core.database.offline.OfflineDatabase;
import core.database.offline.OfflineRegistrationHandler;
import core.database.offline.OfflineSettingHandler;
import core.database.offline.OfflineStreamHandler;
import core.database.offline.OfflineStringMessageHandler;
import core.database.offline.OfflineUserHandler;
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
    public static RegistrationHandler registrationHandler;
    public static SettingHandler settingHandler;
    public static StreamHandler streamHandler;
    public static StringMessageHandler stringMessageHandler;
    public static UserHandler userHandler;
    public static Database database;

    public static boolean setOffline() {
        registrationHandler =new OfflineRegistrationHandler();
        settingHandler =new OfflineSettingHandler();
        streamHandler =new OfflineStreamHandler();
        stringMessageHandler =new OfflineStringMessageHandler();
        userHandler =new OfflineUserHandler();
        database =new OfflineDatabase();
        
        return database.connect();
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
