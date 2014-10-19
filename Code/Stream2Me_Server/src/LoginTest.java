
import biz.source_code.base64Coder.Base64Coder;
import channel.ClientChannel;
import channel.group.ClientHandler;
import channel.group.matcher.ClientGroup;
import connection.messageChannel.MessageBuilder;
import core.database.DatabaseHandler;
import core.database.abstractInterface.Database;
import core.database.objects.User;
import core.database.online.OnlineDatabase;
import core.database.online.OnlineUserHandler;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Bernhard
 */
public class LoginTest {
    static MessageDigest digest;

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        digest =MessageDigest.getInstance(Database.ENCRYPTION);
        
        String password ="cos301";
        String userID ="029a4095-4588-4c8a-b416-8e5ddbf7b585";
        
        String pwd = getHex(getPassword(password, userID));
        
        byte[] dbDigest =pwd.getBytes();
        password =userID+password;
        byte[] pwdDigest =getHex(digest.digest(password.getBytes())).getBytes();
        boolean digestMatch =MessageDigest.isEqual(pwdDigest, dbDigest);
        
        System.out.println(pwd);
        
        System.out.println(digestMatch);
    }
    
    public static String getHex(byte[] b){
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++){
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }
    
    private static byte[] getPassword(String pass, String key) throws UnsupportedEncodingException {
        String pwd =key+pass;
        byte[] pwdDigest =digest.digest(pwd.getBytes());
        return pwdDigest;
    }
}
