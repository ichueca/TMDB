/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package org.zabalburu.daw1.tmdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.zabalburu.daw1.tmdb.UsuarioNoExisteException;
import org.zabalburu.daw1.tmdb.modelo.Usuario;
import org.zabalburu.daw1.util.Conexion;
import org.zabalburu.daw1.util.PasswordManager;


/**
 *
 * @author ichueca
 */
public class UsuarioImplTest {
    private static Connection cnn;
    private static UsuarioDAO dao = new UsuarioImpl();
    MockedStatic<Conexion> conn;
    
    public UsuarioImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() throws SQLException {
        cnn = DriverManager.getConnection(
                "jdbc:derby:memory:tests;create=true");
        try {
            cnn.createStatement()
                .executeUpdate("Drop table usuarios");
        } catch (SQLException ex){}
        cnn.createStatement()
                .executeUpdate(
                        """
                Create Table Usuarios(
                id integer,
                nombre varchar(100),
                apellidos varchar(100),
                usuario varchar(100),
                hash varchar(100),
                email varchar(100),
                imagen varchar(100))
                """);
    }
    
    @AfterAll
    public static void tearDownClass() throws SQLException {
        cnn.close();
    }
    
    @BeforeEach
    public void setUp() throws SQLException {
        PreparedStatement pst = cnn.prepareStatement("""
            insert into Usuarios 
            values (?,?,?,?,?,?,?)                                                                        
            """);
        pst.setInt(1, 1);
        pst.setString(2, "Juan");
        pst.setString(3, "López");
        pst.setString(4, "jlopez");
        pst.setString(5, PasswordManager.encriptarContraseña("12345"));
        pst.setString(6, "jlopez@gmail.com");
        pst.setString(7, "noimage");
        pst.executeUpdate();
        pst.setInt(1, 2);
        pst.setString(2, "Ana");
        pst.setString(3, "Sanz");
        pst.setString(4, "asanz");
        pst.setString(5, PasswordManager.encriptarContraseña("12345"));
        pst.setString(6, "asanz@gmail.com");
        pst.setString(7, "noimage");
        pst.executeUpdate();
        pst.close();
        conn = Mockito.mockStatic(Conexion.class);
        UsuarioImpl.setCnn(cnn);
        conn.when(() -> Conexion.getConexion()).thenReturn(cnn);
    }
    
    @AfterEach
    public void tearDown() throws SQLException {
        deleteAll();
        conn.close();
    }

    private void deleteAll() throws SQLException{
        cnn.createStatement().executeUpdate(
        "delete from usuarios where 1=1");
    }
    
    /**
     * Test of getUsuario method, of class UsuarioImpl.
     */
    @Test
    public void testGetUsuario_Integer() {
        Usuario juan = dao.getUsuario(1);
        assertNotNull(juan);
        assertEquals(
                "asanz@gmail.com",
                dao.getUsuario(2).getEmail());
        assertThrows(
        UsuarioNoExisteException.class, 
            () -> {
                dao.getUsuario(4);
            });
    }

    /**
     * Test of getUsuario method, of class UsuarioImpl.
     */
    @Test
    public void testGetUsuario_String() {
        assertNotNull(dao.getUsuario("jlopez"));
        assertEquals(
                "asanz@gmail.com",
                dao.getUsuario("asanz").getEmail());
        assertThrows(
        UsuarioNoExisteException.class, 
            () -> {
                dao.getUsuario("mtorre");
            });
    }

    /**
     * Test of getUsuarios method, of class UsuarioImpl.
     */
    @Test
    public void testGetUsuarios() throws SQLException {
        List<Usuario> usuarios = dao.getUsuarios();
        assertEquals(2, usuarios.size());
        assertEquals("Juan", usuarios.get(0).getNombre());
        deleteAll();
        assertEquals(0, dao.getUsuarios().size());
    }

    /**
     * Test of nuevoUsuario method, of class UsuarioImpl.
     */
    @Test
    public void testNuevoUsuario() {
        Usuario nuevo = new Usuario(0,
                "nuevo",
                "nuevo",
                "nuevo",
            PasswordManager.encriptarContraseña("12345"),
            "nuevo@gmail.com",
                "noimage");
        nuevo = dao.nuevoUsuario(nuevo);
        assertEquals(3, nuevo.getId());
        assertNotNull(dao.getUsuario(3));
        assertEquals("nuevo",dao.getUsuario("nuevo").getNombre());
    }

    /**
     * Test of modificarUsuario method, of class UsuarioImpl.
     */
    @Test
    public void testModificarUsuario() {
        Usuario modificar = dao.getUsuario("asanz");
        modificar.setApellido("López De Simón");
        assertEquals(
                "López De Simón", 
                dao.modificarUsuario(modificar).getApellido());
        assertEquals(
                "López De Simón", 
                dao.getUsuario(2).getApellido());
        
    }

    /**
     * Test of eliminarUsuario method, of class UsuarioImpl.
     */
    @Test
    public void testEliminarUsuario() {
        dao.eliminarUsuario(1);
        assertEquals(1,dao.getUsuarios().size());
        assertThrows(UsuarioNoExisteException.class, 
                () -> dao.getUsuario(1));
        assertThrows(UsuarioNoExisteException.class, 
                () -> dao.getUsuario("jlopez"));
    }
    
}
