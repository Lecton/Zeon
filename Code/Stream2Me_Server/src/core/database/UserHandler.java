/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database;

import channel.ClientChannel;
import channel.ClientHandler;
import channel.group.matcher.ClientGroup;
import connection.messageChannel.MessageBuilder;
import core.database.objects.User;
import io.netty.channel.Channel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public static GreetingMessage userLogin(Channel ch, LoginMessage message) {
        String username =message.getUsername();
        String password =message.getPasswordHash();
        
        if(username != null){
            PreparedStatement statement;
            ResultSet result = null;
            String query = "SELECT * " +
                            "FROM client " +
                            "WHERE username = ? ";
            statement = Database.INSTANCE.getPreparedStatement(query);
            try{
                statement.setString(1, username);
//                statement.setString(2, password);
                System.out.println("Pwd: "+password);
                System.out.println("Query: "+statement.toString());
                result = statement.executeQuery();
                
                if(result.next()){
                    String userID =result.getString("userid");
                    String groupID =result.getString("groupid");
                    String name =result.getString("name");
                    String surname =result.getString("surname");
                    String uName =result.getString("username");
                    String email =result.getString("email");
                    String aboutMe =result.getString("aboutme");
                    String title =result.getString("title");
                    String avatar =result.getString("avatar");
                    boolean loggedIn =result.getBoolean("loggedin");
                    System.out.println(loggedIn);
                    if (!loggedIn) {
                        User person =new User(userID, uName, groupID, 
                                name, surname, email, title, aboutMe, avatar);
                        ClientChannel cc =new ClientChannel(ch, userID, groupID);
                        boolean addResult =ClientHandler.add(cc);
                        if (addResult) {
                            ClientHandler.writeAndFlush(groupID, MessageBuilder.generateNewUser(person), new ClientGroup(groupID));
                            //update db to set loggedin to true;
                            return MessageBuilder.generateGreeting(person, true, "Login success.");
                        } else {
                            return MessageBuilder.generateGreeting(null, false, "Users group could not be found.");
                        }
                    } else {
                        return MessageBuilder.generateGreeting(null, false, "User already logged in.");
                    }
                }
              }catch (SQLException ex) {
                  Logger.getLogger(UserHandler.class.getName())
                          .log(Level.SEVERE, null, ex);
              }finally{
                  try{
                      if(statement != null){
                          statement.close();
                      }
                  }catch(SQLException ex){
//                      System.out.println("here2");
                      Logger.getLogger(UserHandler.class.getName())
                              .log(Level.SEVERE, null, ex);
                  }
              }
        }
        return MessageBuilder.generateGreeting(null, false, "Login failed. Username or password incorrect.");
//        throw new UnsupportedOperationException("Login error."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Gets the user with the corresponding userID
     * @param userID - ID of the user to be retrieved
     * @return User if found, otherwise null
     */
    public static User getUser(String userID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Logs off the user with the provided user id
     * @param userID - ID of the user
     */
    public static void logoff(String userID) {
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
    public static String[] getGroupUsers(String userID) {
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
    public static NewUserMessage getNewUserMessage(String userID, String targetID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void generateGroups() {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT groupid " +
                        "FROM collection";
        statement = Database.INSTANCE.getPreparedStatement(query);
        try{
            result = statement.executeQuery();

            while(result.next()){
                String groupID =result.getString("groupid");
                ClientHandler.createGroup(groupID);
            }
          }catch (SQLException ex) {
              Logger.getLogger(UserHandler.class.getName())
                      .log(Level.SEVERE, null, ex);
          }finally{
              try{
                  if(statement != null){
                      statement.close();
                  }
              }catch(SQLException ex){
//                      System.out.println("here2");
                  Logger.getLogger(UserHandler.class.getName())
                          .log(Level.SEVERE, null, ex);
              }
          }

    }
}
