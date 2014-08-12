/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

/**
 *
 * @author Bernhard
 */
public class Log {
    
    public static void write(Object parentComponent, String message) {
        System.out.println(parentComponent.getClass().getSimpleName()+" - "+message);
    }
    
    public static void error(Object parentComponent, String message) {
        System.err.println(parentComponent.getClass().getSimpleName()+" - "+message);
    }
}
