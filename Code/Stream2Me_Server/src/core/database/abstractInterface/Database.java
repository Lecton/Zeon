/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database.abstractInterface;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public interface Database {
    static String ENCODING ="LATIN1";
    
    public abstract boolean connect();
    
    public abstract boolean close();
    
    public abstract String getPassword(String pass, String key) throws UnsupportedEncodingException;
}
