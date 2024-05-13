/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.tmdb.servicio;

import java.util.List;
import org.zabalburu.daw1.tmdb.dao.UsuarioDAO;
import org.zabalburu.daw1.tmdb.dao.UsuarioImpl;
import org.zabalburu.daw1.tmdb.exceptions.UsuarioNoExisteException;
import org.zabalburu.daw1.tmdb.modelo.Usuario;
import org.zabalburu.daw1.util.PasswordManager;

/**
 *
 * @author ichueca
 */
public class TMDBServicio {
    
    private UsuarioDAO usuarioDao = new UsuarioImpl();

    public TMDBServicio(){
        
    }
    
    public UsuarioDAO getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDAO usuarioDao) {
        this.usuarioDao = usuarioDao;
    }
    
    public Usuario getUsuario(Integer id){
        return usuarioDao.getUsuario(id);
    }
    
    public List<Usuario> getUsuarios(){
        return usuarioDao.getUsuarios();
    }
    
    public Usuario getUsuario(String usuario){
        return usuarioDao.getUsuario(usuario);
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
        /*Usuario u = usuarioDao.getUsuarios()
                .stream()
                .filter(us -> us.getEmail().equalsIgnoreCase(email))
                .findAny()
                .orElse(null);
        return u == null;*/
        List<Usuario> usuarios = usuarioDao.getUsuarios();
        boolean encontrado = false;
        for (int i = 0; i < usuarios.size() && 
                !encontrado; i++) {
            if (usuarios.get(i).getEmail()
                .equalsIgnoreCase(email)){
                encontrado = true;
            }
        }
        return !encontrado;
    }
    
    public Usuario login(String usuario, String password){
        Usuario u = null;
        try {
            u = usuarioDao.getUsuario(usuario);
        } catch (UsuarioNoExisteException ex){}
        if (u != null){
            if (!PasswordManager.comprobarContraseña(password, u.getPassword())){
                u = null;
            }
        }
        return u;
    }
}
