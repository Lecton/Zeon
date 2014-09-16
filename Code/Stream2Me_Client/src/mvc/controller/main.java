/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

/**
 *
 * @author Bernhard
 */
public class main {
    public static void main(String[] args) throws InterruptedException {
        Control c =new Control("localhost", 2014);
        Control.INSTANCE =c;
        c.initiate(0);
    }
}
