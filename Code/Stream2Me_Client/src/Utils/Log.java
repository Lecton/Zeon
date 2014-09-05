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
    
    public static void write(final Class parentClass, final String message) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(parentClass.getSimpleName()+" - "+message);
            }
        })).start();
    }
    
    public static void error(final Class parentClass, final String message) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println(parentClass.getSimpleName()+" - "+message);
            }
        })).start();
    }
    
    public static void error(final Class parentClass, final int message) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
        System.err.println(parentClass.getSimpleName()+" - "+message);
            }
        })).start();
    }

    public static void blank() {
    }
}
