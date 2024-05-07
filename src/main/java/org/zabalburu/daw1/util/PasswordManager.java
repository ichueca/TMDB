/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ichueca
 */
public final class PasswordManager {
    
    private PasswordManager(){
    }
    
    public static String encriptarContraseña(String password){
        String salto = BCrypt.gensalt();
        return BCrypt.hashpw(password, salto);
    }
    
    public static boolean comprobarContraseña(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
}
