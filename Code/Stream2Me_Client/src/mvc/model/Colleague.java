/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model;

import mvc.model.person.Receiver;

/**
 *
 * @author Bernhard
 */
public class Colleague extends Receiver {

    public Colleague(String userID, String name, String surname, String email, String avatar, String title, String aboutMe) {
        super(userID, name, surname, email, avatar, title, aboutMe);
    }

}
