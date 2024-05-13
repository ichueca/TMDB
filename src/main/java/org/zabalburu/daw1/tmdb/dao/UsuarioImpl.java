/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.tmdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zabalburu.daw1.tmdb.exceptions.UsuarioNoExisteException;
import org.zabalburu.daw1.tmdb.modelo.Usuario;
import org.zabalburu.daw1.util.Conexion;
import org.zabalburu.daw1.util.PasswordManager;

/**
 *
 * @author ichueca
 */
public class UsuarioImpl implements UsuarioDAO {

    private static Connection cnn = Conexion.getConexion();

    public static Connection getCnn() {
        return cnn;
    }

    public static void setCnn(Connection cnn) {
        UsuarioImpl.cnn = cnn;
    }

    @Override
    public Usuario getUsuario(Integer id) throws UsuarioNoExisteException {
        Usuario u = null;
        try {
            PreparedStatement pst = cnn.prepareStatement(
                    """
                                Select * From Usuarios
                                Where id=?
                                """);
            pst.setInt(1, id);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                u = crearUsuario(rst);
            } else {
                throw new UsuarioNoExisteException();
            }
            rst.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }

    @Override
    public Usuario getUsuario(String usuario) throws UsuarioNoExisteException {
        Usuario u = null;
        try {
            PreparedStatement pst = cnn.prepareStatement(
                    """
                                Select * From Usuarios
                                Where upper(usuario)=upper(?)
                                """);
            pst.setString(1, usuario);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                u = crearUsuario(rst);
            } else {
                throw new UsuarioNoExisteException();
            }
            rst.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }

    @Override
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            ResultSet rst = cnn.createStatement()
                    .executeQuery(
                            """
                            Select * From Usuarios
                            """);
            while (rst.next()) {
                usuarios.add(crearUsuario(rst));
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    @Override
    public Usuario nuevoUsuario(Usuario nuevo) {
        try {
            // Obtener el máximo id
            ResultSet rst = cnn.createStatement()
                    .executeQuery(
                            """
                            Select max(id) maxId From Usuarios
                            """);
            rst.next();
            int maxId = rst.getInt("maxId") + 1;
            rst.close();
            nuevo.setId(maxId);
            PreparedStatement pst = cnn.prepareStatement(
                    """
                insert into usuarios(id,nombre,apellidos,usuario,hash,email,imagen)
                values(?,?,?,?,?,?,?)
                """);
            pst.setInt(1, maxId);
            pst.setString(2, nuevo.getNombre());
            pst.setString(3, nuevo.getApellido());
            pst.setString(4, nuevo.getUsuario());
            // Obetenemos el hash del password
            pst.setString(5, PasswordManager.encriptarContraseña(nuevo.getPassword()));
            pst.setString(6, nuevo.getEmail());
            pst.setString(7, nuevo.getImagen());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nuevo;
    }

    @Override
    public Usuario modificarUsuario(Usuario modificar) {
        try {
            PreparedStatement pst = cnn.prepareStatement(
                    """
                        Update usuarios
                        set nombre=?, apellidos=?, email=?, imagen=?
                        where id=?
                        """);
            pst.setString(1, modificar.getNombre());
            pst.setString(2, modificar.getApellido());
            pst.setString(3, modificar.getEmail());
            pst.setString(4, modificar.getImagen());
            pst.setInt(5, modificar.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modificar;
    }

    @Override
    public void eliminarUsuario(Integer id) {
        try {
            PreparedStatement pst = cnn.prepareStatement(
                    """
                    Delete From usuarios
                    where id=?
                    """);
            pst.setInt(1, id);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Usuario crearUsuario(ResultSet rst) {
        Usuario u = new Usuario();
        try {
            u.setId(rst.getInt("id"));
            u.setApellido(rst.getString("apellidos"));
            u.setEmail(rst.getString("email"));
            u.setImagen(rst.getString("imagen"));
            u.setNombre(rst.getString("nombre"));
            u.setPassword(rst.getString("hash")); // HASH
            u.setUsuario(rst.getString("usuario"));
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }

    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioImpl();
        System.out.println(dao.getUsuario(10));

        /*Usuario nuevo = new Usuario(0, "Juan", "López Marín",
            "borrar",
            "12345",
            "borrar@gmail.com",
            "borrar.jpg");
            dao.nuevoUsuario(nuevo);
            Usuario usuario = dao.getUsuario(2);
            System.out.println(usuario);
            //usuario.setApellido("Sanz De la Maza");
            //dao.modificarUsuario(usuario);
            //System.out.println(dao.getUsuario("ichueca"));
            System.out.println(dao.getUsuario("borrar"));
            System.out.println("=============================");
            for (Usuario usu : dao.getUsuarios()) {
            System.out.println(usu);
            }
            dao.eliminarUsuario(2);
            System.out.println("=============================");
            for (Usuario usu : dao.getUsuarios()) {
            System.out.println(usu);
            }*/
    }

}
