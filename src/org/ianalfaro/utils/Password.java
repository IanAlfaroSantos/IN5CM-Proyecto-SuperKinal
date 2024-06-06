/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author alfar
 */
public class Password{
    private static Password instance;
    
    private Password(){
        
    }

    public static Password getInstance(){
        if(instance == null){
            instance = new Password();
        }
        return instance;
    }
    
    public String encryptedPassword(String pass){
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }
    
    public boolean checkPassword(String pass, String encryptedPass){
        return BCrypt.checkpw(pass, encryptedPass);
    }   
}