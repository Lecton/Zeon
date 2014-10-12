/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database.abstractInterface;

import channel.ClientChannel;
import core.database.objects.BaseUser;
import io.netty.channel.Channel;
import messages.Message;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateProfileMessage;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LoginMessage;
import messages.userConnection.NewUserMessage;

/**
 *
 * @author Bernhard
 */
public interface UserHandler {

    /**
     * Checks if the username and password match. If there is a match and 
     * the user is not logged in then a greeting message is generated with
     * a success. If there is a mismatch or already logged in state then a 
     * false greeting is generated. 
     * 
     * The channel is populated with the static user data
     * @param ch - The channel of the user to be logged in
     * @param message - The LoginMessage containing the users login details
     * @return GreetingMessage with success or failure data
     */
    public abstract GreetingMessage userLogin(Channel ch, LoginMessage message);

    /**
     * Gets the base user with the corresponding userID
     * The base user is the same as the user except it only
     * holds the userID, groupID and loggedin fields.
     * @param userID - ID of the user to be retrieved
     * @return BaseUser if found, otherwise null
     */
    public abstract BaseUser getUser(String userID);

    /**
     * Logs off the user with the provided user id
     * @param userID - ID of the user
     */
    public abstract void logoff(String userID, String connectionID);

    /**
     * Update the avatar string of the user and retrieves the groupID of the user,
     * if the user has a group.
     * @param msg - Message containing the updated avatar and userID
     * @return Possibly modified UpdateAvatarMessage
     */
    public abstract Message updateAvatar(UpdateAvatarMessage msg);

    /**
     * Update the profile of the user and retrieves the groupID of the user,
     * if the user has a group.
     * @param msg - Message containing the updated profile details and userID
     * @return Possibly modified UpdateNameMessage
     */
    public abstract Message updateProfile(UpdateProfileMessage msg);

    /**
     * Gets the groupID of the user provided and returns an array of all the users
     * in the specified group. If the user is not in a group then an empty array
     * is returned
     * @param userID - ID of the user to find
     * @return users in the group or an empty (int[0]) array
     */
    public abstract String[] getGroupUsers(String userID);

    /**
     * Gets the user with the specified userID and returns a NewUserMessage populated
     * with the specified users details. A additional check if the user is still in
     * the group is done against the targets group ID. The target of the returned message
     * is set to the provided target ID.
     * @param userID - ID of the user to find
     * @return generated NewUserMessage of the user provided or null if a mismatch or error occurred
     */
    public abstract NewUserMessage getNewUserMessage(String userID, String targetID);
    

    /**
     * Iterates through the database and generates all the groups in the DB
     */
    public abstract void generateGroups();

    /**
     * Gets the groupID of the user provided and returns the ID. If the user
     * could not be found then the Default Group Flag is returned
     * @param userID - ID of the user to find
     * @return users group ID.
     */
    public abstract String getGroupID(String userID);

    public abstract String getConnectionID(String userID);
    
    public abstract void changeUserGroup(ClientChannel cc, String userID, String groupID);
}
