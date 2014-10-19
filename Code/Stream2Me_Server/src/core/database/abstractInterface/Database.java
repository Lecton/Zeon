/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database.abstractInterface;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public interface Database {
    static String ENCRYPTION ="SHA-512";
    
    public abstract boolean connect();
    
    public abstract boolean close();
    
    public abstract boolean validatePassword(String userID, String password, String pwd);
    
    public abstract String getPasswordForDatabase(String userID, String password);
    
    public abstract String getHex(byte[] b);
}
