/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.util.logging.Logger;

/**
 *
 * @author Bernhard
 */
public class main {
    private final static Logger LOGGER = Logger.getLogger(main.class.getName());
    
    public static void main(String[] args) throws InterruptedException {
//        Control c =new Control("192.168.1.31", 2014);
        Control c =new Control();
        Control.INSTANCE =c;
        c.initiate(-1);
    }
}
