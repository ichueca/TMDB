/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.tmdb.servicio;

import java.util.List;
import org.zabalburu.daw1.tmdb.dao.UsuarioDAO;
import org.zabalburu.daw1.tmdb.dao.UsuarioImpl;
import org.zabalburu.daw1.tmdb.modelo.Usuario;
import org.zabalburu.daw1.util.PasswordManager;

/**
 *
 * @author ichueca
 */
public class TMDBServicio {
    
    private UsuarioDAO usuarioDao = new UsuarioImpl();
    
    public Usuario getUsuario(Integer id){
        return usuarioDao.getUsuario(id);
    }
    
    public List<Usuario> getUsuarios(){
        return usuarioDao.getUsuarios();
    }
    
    public Usuario nuevoUsuario(Usuario nuevo){
        return usuarioDao.nuevoUsuario(nuevo);
    }
    
    public Usuario modificarUsuario(Usuario modificar){
        return usuarioDao.modificarUsuario(modificar);
    }
    
    public void eliminarUsuario(Integer id){
        usuarioDao.eliminarUsuario(id);
    }
    
    public boolean emailDisponible(String email){
        Usuario u = usuarioDao.getUsuarios()
                .stream()
                .filter(us -> us.getEmail().equalsIgnoreCase(email))
                .findAny()
                .orElse(null);
        return u == null;
    }
    
    public Usuario login(String usuario, String password){
        Usuario u = usuarioDao.getUsuario(usuario);
        if (u != null){
            if (!PasswordManager.comprobarContraseña(password, u.getPassword())){
                u = null;
            }
        }
        return u;
    }
}
