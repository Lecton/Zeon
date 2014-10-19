/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database.offline;

import biz.source_code.base64Coder.Base64Coder;
import channel.ClientChannel;
import channel.group.ClientHandler;
import channel.group.matcher.ClientGroup;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oIOException;
import connection.messageChannel.MessageBuilder;
import core.database.abstractInterface.UserHandler;
import core.database.objects.BaseUser;
import core.database.offline.object.Client;
import core.database.offline.object.Collection;
import core.database.offline.object.Connection;
import core.database.offline.object.StreamData;
import core.database.offline.object.StreamProp;
import io.netty.channel.Channel;
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
public class OfflineUserHandler implements UserHandler {

    private void userLoggedInUpdate(String userID, String connectionID, boolean action) {
        try {
            if (action) {
                Connection con =new Connection(connectionID, userID);
                OfflineDatabase.INSTANCE.db.store(con);
                OfflineDatabase.INSTANCE.db.commit();
                
                ObjectSet<Client> result =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
                Client c =result.next();
                c.setLoggedin(true);
                OfflineDatabase.INSTANCE.db.store(c);
                OfflineDatabase.INSTANCE.db.commit();
                removeAllOther(connectionID, userID);
            } else {
                ObjectSet<Connection> resultCon =OfflineDatabase.INSTANCE.db.queryByExample(new Connection(connectionID, userID));
                Connection con =resultCon.next();
                OfflineDatabase.INSTANCE.db.delete(con);
                OfflineDatabase.INSTANCE.db.commit();
                
                ObjectSet<Client> result =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
                Client c =result.next();
                c.setLoggedin(false);
                OfflineDatabase.INSTANCE.db.store(c);
                OfflineDatabase.INSTANCE.db.commit();
                removeAllOther(connectionID, userID);
            }
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineDatabase.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public GreetingMessage userLogin(Channel ch, LoginMessage msg) {
        try {
            String username =msg.getUsername();
            String password =Base64Coder.decodeString(msg.getPasswordHash());

            if(username != null) {
                try {
                    ObjectSet<Client> result =OfflineDatabase.INSTANCE.db.queryByExample(Client.queryUsername(msg.getUsername()));
                    Client user =result.next();
                    if (result.size() == 1) {
                        String userID =user.getUserid();
                        String pwd =user.getPassword();

                        boolean digestMatch =OfflineDatabase.INSTANCE.validatePassword(userID, password, pwd);

                        if (digestMatch) {
                            if (!user.isLoggedin()) {
                                ClientChannel cc =new ClientChannel(ch, userID, user.getGroup().getGroupID());
                                int addResult =ClientHandler.add(cc);
                                if (addResult == 1) {
                                    ClientHandler.writeAndFlush(user.getGroup().getGroupID(), MessageBuilder.generateNewUser(user.getUser(), null), new ClientGroup(userID, user.getGroup().getGroupID()));
                                    userLoggedInUpdate(userID, cc.getConnectionID(), true);
                                    return MessageBuilder.generateGreeting(user.getUser(), true, "Login success.");
                                } else if (addResult == 2) {
                                    userLoggedInUpdate(userID, cc.getConnectionID(), true);
                                    return MessageBuilder.generateGreeting(user.getUser(), true, "Login success.");
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
                } catch (Db4oIOException ex) {
                    Logger.getLogger(OfflineUserHandler.class.getName())
                            .log(Level.SEVERE, null, ex);
                } catch (IllegalStateException ex) {
                    Logger.getLogger(OfflineUserHandler.class.getName())
                            .log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(OfflineUserHandler.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return MessageBuilder.generateGreeting(null, false, "Login failed. Username or password incorrect.");
    }

    @Override
    public BaseUser getUser(String userID) {
        try {
            ObjectSet<Client> result =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
            Client person =result.next();
            return person.getUser();
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void logoff(String userID, String connectionID) {
        userLoggedInUpdate(userID, connectionID, false);
        
    }

    @Override
    public Message updateAvatar(UpdateAvatarMessage msg) {
        try {
            ObjectSet<Client> result =OfflineDatabase.INSTANCE.db.queryByExample(new Client(msg.getUserID()));
            Client person =result.next();
            person.setAvatar(msg.getAvatar());
            OfflineDatabase.INSTANCE.db.store(person);
            OfflineDatabase.INSTANCE.db.commit();
            msg.setTargetGroupID(getGroupID(msg.getUserID()));
            return msg;
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.INFO, null, ex);
        }
        return null;
    }

    

    @Override
    public Message updateProfile(UpdateProfileMessage msg) {
        try {
            ObjectSet<Client> result =OfflineDatabase.INSTANCE.db.queryByExample(new Client(msg.getUserID()));
            Client person =result.next();
            person.setName(msg.getName());
            person.setSurname(msg.getSurname());
            person.setEmail(msg.getEmail());
            person.setTitle(msg.getTitle());
            person.setAboutme(msg.getAboutMe());
            OfflineDatabase.INSTANCE.db.store(person);
            OfflineDatabase.INSTANCE.db.commit();
            msg.setTargetGroupID(getGroupID(msg.getUserID()));
            return msg;
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String[] getGroupUsers(String userID) {
        BaseUser bu =getUser(userID);
        if (bu != null) {
            try {
                ObjectSet<Client> personResult =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
                Client person =personResult.next();
                
                Client example =new Client();
                example.setGroup(person.getGroup());
                example.setLoggedin(true);
                ObjectSet<Client> result =OfflineDatabase.INSTANCE.db.queryByExample(example);
                
                List<String> users =new ArrayList<>();
                while (result.hasNext()) {
                    Client c =result.next();
                    users.add(c.getUserid());
                }
                return users.toArray(new String[0]);
            } catch (Db4oIOException ex) {
                Logger.getLogger(OfflineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return new String[0];
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NewUserMessage getNewUserMessage(String userID, String targetID) {
        try {
            ObjectSet<Client> result =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
            Client person =result.next();
            return MessageBuilder.generateNewUser(person.getUser(), targetID);
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public void generateGroups() {
        try {
            ObjectSet<Collection> result =OfflineDatabase.INSTANCE.db.query(Collection.class);
            while (result.hasNext()) {
                ClientHandler.createGroup(result.next().getGroupID());
            }
          } catch (Db4oIOException ex) {
              Logger.getLogger(OfflineUserHandler.class.getName())
                      .log(Level.SEVERE, null, ex);
          }
    }

    @Override
    public String getGroupID(String userID) {
        try {
            ObjectSet<Client> result =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
            Client person =result.next();
            return person.getGroup().getGroupID();
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return "default";
    }

    @Override
    public String getConnectionID(String userID) {
        try {
            ObjectSet<Connection> result =OfflineDatabase.INSTANCE.db.queryByExample(new Connection(null, userID));
            while(result.hasNext()) {
                return result.next().getConnectionid();
            }
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public void changeUserGroup(ClientChannel cc, String userID, String groupID) {
        try {
            ObjectSet<Client> result =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
            Client person =result.next();
            cc.setGroupID(groupID);
            int addResult =ClientHandler.add(cc);
            if (addResult == 1) {
                ClientHandler.writeAndFlush(groupID, MessageBuilder.generateNewUser(person.getUser(), null), new ClientGroup(userID, groupID));
            } else if (addResult == 2) {
                userLoggedInUpdate(userID, cc.getConnectionID(), true);
            }
          }catch (Db4oIOException ex) {
              Logger.getLogger(OfflineUserHandler.class.getName())
                      .log(Level.SEVERE, null, ex);
          }
    }

    private void removeAllOther(String connectionID, String userID) {
        ObjectSet<Client> resultUser =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
        Client user =resultUser.next();
        
        ObjectSet<StreamData> resultSD =OfflineDatabase.INSTANCE.db.queryByExample(StreamData.queryUser(userID, connectionID));
        for (StreamData sd: resultSD) {
            OfflineDatabase.INSTANCE.db.delete(sd);
        }
        OfflineDatabase.INSTANCE.db.commit();
        
        ObjectSet<StreamProp> resultSP =OfflineDatabase.INSTANCE.db.queryByExample(StreamProp.queryUser(user));
        for (StreamProp sp: resultSP) {
            resultSD =OfflineDatabase.INSTANCE.db.queryByExample(StreamData.query(sp.getStreamID()));
            for (StreamData sd: resultSD) {
                OfflineDatabase.INSTANCE.db.delete(sd);
            }
            OfflineDatabase.INSTANCE.db.delete(sp);
            OfflineDatabase.INSTANCE.db.commit();
        }
    }
}
