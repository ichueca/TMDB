/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.tmdb.dao;

import java.util.List;
import org.zabalburu.daw1.tmdb.UsuarioNoExisteException;
import org.zabalburu.daw1.tmdb.modelo.Usuario;

/**
 *
 * @author ichueca
 */
public interface UsuarioDAO {
    Usuario getUsuario(Integer id) throws UsuarioNoExisteException;
    Usuario getUsuario(String usuario) throws UsuarioNoExisteException;
    List<Usuario> getUsuarios();
    Usuario nuevoUsuario(Usuario nuevo);
    Usuario modificarUsuario(Usuario modificar);
    void eliminarUsuario(Integer id);
}
