/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database.online;

import biz.source_code.base64Coder.Base64Coder;
import channel.ClientChannel;
import channel.group.ClientHandler;
import channel.group.matcher.ClientGroup;
import connection.messageChannel.MessageBuilder;
import core.database.abstractInterface.Database;
import core.database.abstractInterface.UserHandler;
import core.database.objects.BaseUser;
import core.database.objects.User;
import io.netty.channel.Channel;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
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
public class OnlineUserHandler implements UserHandler {
    
    /**
     * Private helper function for the login and logoff functions strictly. 
     * The database is queried for the respective user and he is added to a 
     * connection table.
     * @param userID - User to be found
     * @param connectionID - the socket session ID
     * @param action - Flag to add (true) or remove (false) from the connections
     */
    private void userLoggedInUpdate(String userID, String connectionID, boolean action) {
        PreparedStatement statement =null;
        try{
            if (action) {
                String query = "INSERT INTO connection " +
                        "(connectionID, userID)" +
                        "VALUES (?, ?)";
                statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
                statement.setString(1, connectionID);
                statement.setString(2, userID);
                statement.execute();
                
                query = "UPDATE client " +
                        "set loggedin = true " +
                        "WHERE userid = ?";
                statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
                statement.setString(1, userID);
                statement.executeUpdate();
            } else {
                String query ="DELETE FROM connection " +
                        "WHERE connectionID = ?";
                statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
                statement.setString(1, connectionID);
                statement.execute();
                
                query = "UPDATE client " +
                        "set loggedin = false " +
                        "WHERE userid = ?";
                statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
                statement.setString(1, userID);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try{
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException ex){
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public GreetingMessage userLogin(Channel ch, LoginMessage message) {
        try {
            String username =message.getUsername();
            String password =Base64Coder.decodeString(message.getPasswordHash());

            if(username != null){
                PreparedStatement statement;
                ResultSet result = null;
                String query = "SELECT * " +
                                "FROM client " +
                                "WHERE username = ? ";
                statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
                try {
                    statement.setString(1, username);
    //                statement.setString(2, password);
    //                System.out.println("Pwd: "+password);
    //                System.out.println("Query: "+statement.toString());
                    result = statement.executeQuery();

                    if (result.next()) {
                        String userID =new String(result.getString("userid").getBytes());
                        String pwd =new String(result.getString("password").getBytes());
                        boolean digestMatch =OnlineDatabase.INSTANCE.validatePassword(userID, password, pwd);

                        if (digestMatch) {
                            String groupID =result.getString("groupid");
                            String name =result.getString("name");
                            String surname =result.getString("surname");
                            String uName =result.getString("username");
                            String email =result.getString("email");
                            String aboutMe =result.getString("aboutme");
                            String title =result.getString("title");
                            String avatar =result.getString("avatar");
                            boolean loggedIn =result.getBoolean("loggedin");
                            if (!loggedIn) {
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
                                return MessageBuilder.generateGreeting(null, false, "Multiple logins not allowed.");
                            }
                        } else {
                            return MessageBuilder.generateGreeting(null, false, "Username or password did not match.");
                        }
                    }
                } catch (SQLException ex) {
                      Logger.getLogger(OnlineUserHandler.class.getName())
                              .log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        if(statement != null) {
                            statement.close();
                        }
                    } catch(SQLException ex) {
                        Logger.getLogger(OnlineUserHandler.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return MessageBuilder.generateGreeting(null, false, "Login failed. Username or password incorrect.");
//        throw new UnsupportedOperationException("Login error."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BaseUser getUser(String userID) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT userid, groupid " +
                        "FROM client " +
                        "WHERE userid = ? ";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
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
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public void logoff(String userID, String connectionID) {
        userLoggedInUpdate(userID, connectionID, false);
    }

    @Override
    public Message updateAvatar(UpdateAvatarMessage msg) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "UPDATE client " +
                        "SET avatar = ? " +
                        "WHERE userID = ?";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
        try {
            statement.setString(1, msg.getAvatar());
            statement.setString(2, msg.getUserID());
            int lines =statement.executeUpdate();
            if (lines == 1) {
                msg.setTargetGroupID(getGroupID(msg.getUserID()));
                return msg;
            } else {
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, "Lines changed error", 
                                new SQLException("SQL returned with invalid "
                                        + "line change amount", 
                                        statement.toString()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public Message updateProfile(UpdateProfileMessage msg) {
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
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
        try {
            statement.setString(1, msg.getName());
            statement.setString(2, msg.getSurname());
            statement.setString(3, msg.getEmail());
            statement.setString(4, msg.getTitle());
            statement.setString(5, msg.getAboutMe());
            statement.setString(6, msg.getUserID());
            int lines =statement.executeUpdate();
            if (lines == 1) {
                msg.setTargetGroupID(getGroupID(msg.getUserID()));
                return msg;
            } else {
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, "Lines changed error", 
                                new SQLException("SQL returned with invalid "
                                        + "line change amount", 
                                        statement.toString()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public String[] getGroupUsers(String userID) {
        BaseUser bu =getUser(userID);
        if (bu != null) {
            PreparedStatement statement;
            ResultSet result = null;
            String query = "SELECT DISTINCT con.userid as userid " +
                    "FROM connection as con, client as c " +
                    "WHERE con.userID = c.userID " +
                    "AND c.groupID = ? " +
                    "AND con.userID <> ? ";
            statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
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
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if(statement != null) {
                        statement.close();
                    }
                } catch(SQLException ex) {
                    Logger.getLogger(OnlineUserHandler.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        }
        return new String[0];
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NewUserMessage getNewUserMessage(String userID, String targetID) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT * " +
                        "FROM client " +
                        "WHERE userID = ?";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
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
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    @Override
    public void generateGroups() {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT groupid " +
                        "FROM collection";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
        try {
            result = statement.executeQuery();

            while (result.next()) {
                String groupID =result.getString("groupid");
                ClientHandler.createGroup(groupID);
            }
          } catch (SQLException ex) {
              Logger.getLogger(OnlineUserHandler.class.getName())
                      .log(Level.SEVERE, null, ex);
          } finally {
              try {
                  if(statement != null) {
                      statement.close();
                  }
              } catch(SQLException ex) {
//                      System.out.println("here2");
                  Logger.getLogger(OnlineUserHandler.class.getName())
                          .log(Level.SEVERE, null, ex);
              }
          }
    }

    @Override
    public String getGroupID(String userID) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT groupid "
                        + "FROM client "
                        + "WHERE userid = ?";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
        try{
            statement.setString(1, userID);
            result = statement.executeQuery();

            while(result.next()) {
                String groupID =result.getString("groupid");
                return groupID;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
          //                      System.out.println("here2");
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return "default";
    }

    @Override
    public String getConnectionID(String userID) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT connectionid "
                        + "FROM connection "
                        + "WHERE userid = ?";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
        try{
            statement.setString(1, userID);
            result = statement.executeQuery();

            while(result.next()) {
                String connectionID =result.getString("connectionid");
                return connectionID;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
          //                      System.out.println("here2");
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    @Override
    public void changeUserGroup(ClientChannel cc, String userID, String groupID) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT * " +
                        "FROM client " +
                        "WHERE userID = ? ";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
        try {
            statement.setString(1, userID);
            result = statement.executeQuery();

            if (result.next()) {
                String name =result.getString("name");
                String surname =result.getString("surname");
                String uName =result.getString("username");
                String email =result.getString("email");
                String aboutMe =result.getString("aboutme");
                String title =result.getString("title");
                String avatar =result.getString("avatar");
                boolean loggedIn =result.getBoolean("loggedin");
                User person =new User(userID, uName, groupID, 
                        name, surname, email, title, aboutMe, avatar);
                cc.setGroupID(groupID);
                int addResult =ClientHandler.add(cc);
                if (addResult == 1) {
                    ClientHandler.writeAndFlush(groupID, MessageBuilder.generateNewUser(person, null), new ClientGroup(userID, groupID));
                } else if (addResult == 2) {
                    userLoggedInUpdate(userID, cc.getConnectionID(), true);
                }
            }
          }catch (SQLException ex) {
              Logger.getLogger(OnlineUserHandler.class.getName())
                      .log(Level.SEVERE, null, ex);
        }finally{
              try{
                  if(statement != null){
                      statement.close();
                  }
              }catch(SQLException ex){
//                      System.out.println("here2");
                  Logger.getLogger(OnlineUserHandler.class.getName())
                          .log(Level.SEVERE, null, ex);
              }
          }
    }
}
