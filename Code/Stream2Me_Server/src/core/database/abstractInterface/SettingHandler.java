/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database.abstractInterface;

import core.database.objects.Settings;
import java.util.Map;
import messages.settings.group.GroupCreateMessage;
import messages.settings.group.GroupJoinMessage;

/**
 *
 * @author Bernhard
 */
public interface SettingHandler {
    public abstract Settings getSettings(String userID);

    public abstract Map<String, String> getGroups();

    public abstract GroupJoinMessage groupJoin(String userID, String groupID, String password);

    public abstract GroupCreateMessage groupCreate(String userID, String groupName, String password);
}
