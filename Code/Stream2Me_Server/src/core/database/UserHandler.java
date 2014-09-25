/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database;

import biz.source_code.base64Coder.Base64Coder;
import channel.ClientChannel;
import channel.group.ClientHandler;
import channel.group.matcher.ClientGroup;
import connection.messageChannel.MessageBuilder;
import core.database.objects.BaseUser;
import core.database.objects.User;
import io.netty.channel.Channel;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class UserHandler {
    static MessageDigest digest;
    static {
        try {
            digest =MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Private helper function for the login and logoff functions strictly. 
     * The database is queried for the respective user and he is added to a 
     * connection table.
     * @param userID - User to be found
     * @param connectionID - the socket session ID
     * @param action - Flag to add (true) or remove (false) from the connections
     */
    private static void userLoggedInUpdate(String userID, String connectionID, boolean action) {
        
        PreparedStatement statement =null;
        try{
            if (action) {
                String query = "INSERT INTO connection " +
                        "(connectionID, userID)" +
                        "VALUES (?, ?)";
                statement = Database.INSTANCE.getPreparedStatement(query);
                statement.setString(1, connectionID);
                statement.setString(2, userID);
                statement.execute();
            } else {
                String query ="DELETE FROM connection " +
                        "WHERE connectionID = ?";
                statement = Database.INSTANCE.getPreparedStatement(query);
                statement.setString(1, connectionID);
                statement.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try{
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException ex){
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

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
    public static GreetingMessage userLogin(Channel ch, LoginMessage message) {
        String username =message.getUsername();
        String password =Base64Coder.decodeString(message.getPasswordHash());
        
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
//                System.out.println("Pwd: "+password);
//                System.out.println("Query: "+statement.toString());
                result = statement.executeQuery();
                
                if (result.next()) {
                    String userID =result.getString("userid");
                    String pwd =result.getString("password");
                    
                    byte[] dbDigest =pwd.getBytes("Latin1");
                    password =userID+password;
                    password =password;
                    byte[] pwdDigest =digest.digest(password.getBytes("Latin1"));
                    boolean digestMatch =MessageDigest.isEqual(pwdDigest, dbDigest);
                    
                    if (digestMatch) {
                        String groupID =result.getString("groupid");
                        String name =result.getString("name");
                        String surname =result.getString("surname");
                        String uName =result.getString("username");
                        String email =result.getString("email");
                        String aboutMe =result.getString("aboutme");
                        String title =result.getString("title");
                        String avatar =result.getString("avatar");
                        User person =new User(userID, uName, groupID, 
                                name, surname, email, title, aboutMe, avatar);
                        ClientChannel cc =new ClientChannel(ch, userID, groupID);
                        int addResult =ClientHandler.add(cc);
                        if (addResult == 1) {
                            ClientHandler.writeAndFlush(groupID, MessageBuilder.generateNewUser(person, null), new ClientGroup(userID, groupID));
                            userLoggedInUpdate(userID, cc.getConnectionID(), true);
                            return MessageBuilder.generateGreeting(person, true, "Login success.");
                        } else if (addResult == 2) {
                            userLoggedInUpdate(userID, cc.getConnectionID(), true);
                            return MessageBuilder.generateGreeting(person, true, "Login success.");
                        } else {
                            return MessageBuilder.generateGreeting(null, false, "Users group could not be found.");
                        }
                    } else {
                        return MessageBuilder.generateGreeting(null, false, "Username or password did not match.");
                    }
                }
              }catch (SQLException ex) {
                  Logger.getLogger(UserHandler.class.getName())
                          .log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
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
     * Gets the base user with the corresponding userID
     * The base user is the same as the user except it only
     * holds the userID, groupID and loggedin fields.
     * @param userID - ID of the user to be retrieved
     * @return BaseUser if found, otherwise null
     */
    public static BaseUser getUser(String userID) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT userid, groupid " +
                        "FROM client " +
                        "WHERE userid = ? ";
        statement = Database.INSTANCE.getPreparedStatement(query);
        try {
            statement.setString(1, userID);
            result = statement.executeQuery();

            if(result.next()) {
                String uID =result.getString("userid");
                String groupID =result.getString("groupid");
                
                BaseUser person =new BaseUser(uID, groupID);
                return person;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Logs off the user with the provided user id
     * @param userID - ID of the user
     */
    public static void logoff(String userID, String connectionID) {
        userLoggedInUpdate(userID, connectionID, false);
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
     * Update the profile of the user and retrieves the groupID of the user,
     * if the user has a group.
     * @param msg - Message containing the updated profile details and userID
     * @return Possibly modified UpdateNameMessage
     */
    public static Message updateProfile(UpdateProfileMessage msg) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        PreparedStatement statement;
        ResultSet result = null;
        String query = "UPDATE client " +
                        "SET name = ?, " +
                        "surname = ?, " +
                        "email = ?, " +
                        "title = ?, " +
                        "aboutMe = ? " +
                        "WHERE userID = ?";
        statement = Database.INSTANCE.getPreparedStatement(query);
        try {
            statement.setString(1, msg.getName());
            statement.setString(2, msg.getSurname());
            statement.setString(3, msg.getEmail());
            statement.setString(4, msg.getTitle());
            statement.setString(5, msg.getAboutMe());
            statement.setString(6, msg.getUserID());
            int lines =statement.executeUpdate();
            if (lines == 1) {
                return msg;
            } else {
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, "Lines changed error", 
                                new SQLException("SQL returned with invalid "
                                        + "line change amount", 
                                        statement.toString()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Gets the groupID of the user provided and returns an array of all the users
     * in the specified group. If the user is not in a group then an empty array
     * is returned
     * @param userID - ID of the user to find
     * @return users in the group or an empty (int[0]) array
     */
    public static String[] getGroupUsers(String userID) {
        BaseUser bu =getUser(userID);
        if (bu != null && !bu.getGroupID().equalsIgnoreCase("DEFAULT")) {
            PreparedStatement statement;
            ResultSet result = null;
            String query = "SELECT DISTINCT con.userid as userid " +
                    "FROM connection as con, client as c " +
                    "WHERE con.userID = c.userID " +
                    "AND c.groupID = ? " +
                    "AND con.userID <> ? ";
            statement = Database.INSTANCE.getPreparedStatement(query);
            try {
                statement.setString(1, bu.getGroupID());
                statement.setString(2, bu.getUserID());
                result = statement.executeQuery();

                List<String> users =new ArrayList<>();
                while(result.next()) {
                    String uID =result.getString("userid");
                    users.add(uID);
                }
                return users.toArray(new String[0]);
            } catch (SQLException ex) {
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if(statement != null) {
                        statement.close();
                    }
                } catch(SQLException ex) {
                    Logger.getLogger(UserHandler.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        }
        return new String[0];
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT * " +
                        "FROM client " +
                        "WHERE userID = ?";
        statement = Database.INSTANCE.getPreparedStatement(query);
        try {
            statement.setString(1, userID);
            result = statement.executeQuery();

            if(result.next()) {
                String uID =result.getString("userid");
                String name =result.getString("name");
                String surname =result.getString("surname");
                String email =result.getString("email");
                String title =result.getString("title");
                String aboutMe =result.getString("aboutme");
                String avatar =result.getString("avatar");
                User u =new User(uID, null, null, name, surname, email, title, aboutMe, avatar);
                return MessageBuilder.generateNewUser(u, targetID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    

    /**
     * Iterates through the database and generates all the groups in the DB
     */
    public static void generateGroups() {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT groupid " +
                        "FROM collection";
        statement = Database.INSTANCE.getPreparedStatement(query);
        try {
            result = statement.executeQuery();

            while (result.next()) {
                String groupID =result.getString("groupid");
                ClientHandler.createGroup(groupID);
            }
          } catch (SQLException ex) {
              Logger.getLogger(UserHandler.class.getName())
                      .log(Level.SEVERE, null, ex);
          } finally {
              try {
                  if(statement != null) {
                      statement.close();
                  }
              } catch(SQLException ex) {
//                      System.out.println("here2");
                  Logger.getLogger(UserHandler.class.getName())
                          .log(Level.SEVERE, null, ex);
              }
          }
    }


    /**
     * Gets the groupID of the user provided and returns the ID. If the user
     * could not be found then the Default Group Flag is returned
     * @param userID - ID of the user to find
     * @return users group ID.
     */
    public static String getGroupID(String userID) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT groupid "
                        + "FROM client "
                        + "WHERE userid = ?";
        statement = Database.INSTANCE.getPreparedStatement(query);
        try{
            statement.setString(1, userID);
            result = statement.executeQuery();

            while(result.next()) {
                String groupID =result.getString("groupid");
                return groupID;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
          //                      System.out.println("here2");
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return "default";
    }

    public static String getConnectionID(String userID) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT connectionid "
                        + "FROM connection "
                        + "WHERE userid = ?";
        statement = Database.INSTANCE.getPreparedStatement(query);
        try{
            statement.setString(1, userID);
            result = statement.executeQuery();

            while(result.next()) {
                String connectionID =result.getString("connectionid");
                return connectionID;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
          //                      System.out.println("here2");
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
