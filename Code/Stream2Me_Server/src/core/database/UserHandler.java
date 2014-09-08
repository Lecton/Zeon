/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database;

import channel.ClientChannel;
import core.database.objects.User;
import java.util.ArrayList;
import messages.Message;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateNameMessage;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LoginMessage;
import messages.userConnection.NewUserMessage;

/**
 *
 * @author Bernhard
 */
public class UserHandler {

    /**
     * Checks if the username and password match. If there is a match and 
     * the user is not logged in then a greeting message is generated with
     * a success. If there is a mismatch or already logged in state then a 
     * false greeting is generated. 
     * 
     * The channel is populated with the static user data
     * @param channel - The channel of the user to be logged in
     * @param message - The LoginMessage containing the users login details
     * @return GreetingMessage with success or failure data
     */
    public static GreetingMessage userLogin(ClientChannel channel, LoginMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Gets the user with the corresponding userID
     * @param userID - ID of the user to be retrieved
     * @return User if found, otherwise null
     */
    public static User getUser(int userID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Logs off the user with the provided user id
     * @param userID - ID of the user
     */
    public static void logoff(int userID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Update the avatar string of the user and retrieves the groupID of the user,
     * if the user has a group.
     * @param msg - Message containing the updated avatar and userID
     * @return Possibly modified UpdateAvatarMessage
     */
    public static Message updateAvatar(UpdateAvatarMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Update the name and surname of the user and retrieves the groupID of the user,
     * if the user has a group.
     * @param msg - Message containing the updated name, surname and userID
     * @return Possibly modified UpdateNameMessage
     */
    public static Message updateName(UpdateNameMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Gets the groupID of the user provided and returns an array of all the users
     * in the specified group. If the user is not in a group then an empty array
     * is returned
     * @param userID - ID of the user to find
     * @return users in the group or an empty (int[0]) array
     */
    public static int[] getGroupUsers(int userID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Gets the user with the specified userID and returns a NewUserMessage populated
     * with the specified users details. A additional check if the user is still in
     * the group is done against the targets group ID. The target of the returned message
     * is set to the provided target ID.
     * @param userID - ID of the user to find
     * @return generated NewUserMessage of the user provided or null if a mismatch or error occurred
     */
    public static NewUserMessage getNewUserMessage(int userID, int targetID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
