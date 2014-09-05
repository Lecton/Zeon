/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author Bernhard
 */
public class Log {
    public static void write(Class parentClass, String message) {
        System.out.println(parentClass.getSimpleName()+" - "+message);
    }
    
    public static void error(Class parentClass, String message) {
        System.err.println(parentClass.getSimpleName()+" - "+message);
    }
}
