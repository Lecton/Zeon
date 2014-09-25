
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bernhard
 */
public class SaltyEncryption {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String password ="test";
        String userID ="bca2414d-7ee0-40d9-a130-def36c44dc49";
        
        MessageDigest digest =MessageDigest.getInstance("SHA1");
        String pwd =userID+password;
        byte[] pwdDigest =digest.digest(pwd.getBytes());
        pwd =new String(pwdDigest);
        System.out.println("Pass: "+pwd);
    }
}
