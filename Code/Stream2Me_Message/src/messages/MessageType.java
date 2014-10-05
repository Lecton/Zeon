/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

/**
 *
 * @author Bernhard
 */
public enum MessageType {
    login, logout, newUser,
    
    register, checkUsername, checkEmail, registrationResponse, 
    
    updateProfile, updateAvatar, updateList, 
    
    string, 
    
    streamPropertyRequest, streamProperty, 
    streamUpdate, streamNotify, 
    auido, video, 
    streamReply, streamTerminate, 
    
    greeting, 
    console
}
