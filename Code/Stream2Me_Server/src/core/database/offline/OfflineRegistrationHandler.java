/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database.offline;

import biz.source_code.base64Coder.Base64Coder;
import com.db4o.ObjectSet;
import core.database.abstractInterface.RegistrationHandler;
import core.database.offline.object.Client;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import messages.userConnection.registration.CheckEmailMessage;
import messages.userConnection.registration.CheckUsernameMessage;
import messages.userConnection.registration.RegisterMessage;
import messages.userConnection.registration.RegistrationResponseMessage;

/**
 *
 * @author Bernhard
 */
public class OfflineRegistrationHandler implements RegistrationHandler {

    @Override
    public boolean checkUsername(CheckUsernameMessage msg) {
        ObjectSet<Client> result =OfflineDatabase.INSTANCE.db.queryByExample(Client.queryUsername(msg.getUsername()));
        
        if (result.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkEmail(CheckEmailMessage msg) {
        ObjectSet<Client> result =OfflineDatabase.INSTANCE.db.queryByExample(Client.queryEmail(msg.getEmail()));
        
        if (result.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Message register(RegisterMessage msg) {
        boolean validUsername =checkUsername(new CheckUsernameMessage(msg.getUsername()));
        boolean validEmail =checkEmail(new CheckEmailMessage(msg.getEmail()));
        
        if (validUsername && validEmail) {
            try {
                String username =msg.getUsername();
                String name =msg.getName();
                String surname =msg.getSurname();
                String email =msg.getEmail();
                String password =Base64Coder.decodeString(msg.getPass());
                String uID =UUID.randomUUID().toString();
                Client person =new Client(uID, name, surname, username, OfflineDatabase.INSTANCE.getPasswordForDatabase(uID, password), email, surname);
                OfflineDatabase.INSTANCE.db.store(person);
                OfflineDatabase.INSTANCE.db.commit();
                return new RegistrationResponseMessage(true);
            } catch (Exception ex) {
                Logger.getLogger(OfflineRegistrationHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new RegistrationResponseMessage(false);
    }
}
