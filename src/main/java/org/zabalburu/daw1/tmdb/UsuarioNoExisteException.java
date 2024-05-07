/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.tmdb;

/**
 *
 * @author ichueca
 */
public class UsuarioNoExisteException extends RuntimeException{
    public UsuarioNoExisteException(){
        super("Usuario NO EXISTE!");
    }
}
