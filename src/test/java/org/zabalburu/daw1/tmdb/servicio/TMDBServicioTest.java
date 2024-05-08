/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package org.zabalburu.daw1.tmdb.servicio;

import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.zabalburu.daw1.tmdb.dao.UsuarioImpl;
import org.zabalburu.daw1.tmdb.modelo.Usuario;
import org.zabalburu.daw1.util.PasswordManager;

/**
 *
 * @author ichueca
 */
public class TMDBServicioTest {
    private static UsuarioImpl mock;
    private static TMDBServicio servicio = new TMDBServicio();
    
    public TMDBServicioTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        mock = Mockito.mock(UsuarioImpl.class);
        servicio.setUsuarioDao(mock);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        Mockito.when(
       mock.getUsuarios())
                .thenReturn(
                Arrays.asList(
                    new Usuario(1,"Juan","López","jlopez",
                                        PasswordManager.encriptarContraseña("12345"),
                        "jlopez@gmail.com","noimage"),
                    new Usuario(2,"Ana","Sanz","asanz",
                                        PasswordManager.encriptarContraseña("qwerty"),
                        "asanz@gmail.com","noimage")
                ));
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of emailDisponible method, of class TMDBServicio.
     */
    @Test
    public void testEmailDisponible() {
        assertTrue(servicio.emailDisponible("mtorre@gmail.com"));
        assertFalse(servicio.emailDisponible("asanz@gmail.com"));
        assertFalse(servicio.emailDisponible("ASANZ@gmail.com"));
    }

    /**
     * Test of login method, of class TMDBServicio.
     */
    @Test
    public void testLogin() {
        Usuario juan = new Usuario(1,"Juan","López","jlopez",
                        PasswordManager.encriptarContraseña("qwerty"),
                        "jlopez@gmail.com","noimage");
        Mockito.when(mock.getUsuario("jlopez"))
                .thenReturn(juan);
        Mockito.when(mock.getUsuario("jLopez"))
                .thenReturn(juan);
        Mockito.when(mock.getUsuario("mtorre"))
                .thenReturn(null);
        assertNull(servicio.login("mtorre","12345"));
        assertNull(servicio.login("jlopez","qwertys"));
        assertNull(servicio.login("jlopez","qweRty"));
        assertNotNull(servicio.login("jlopez","qwerty"));
        assertNotNull(servicio.login("jLopez","qwerty"));
        assertEquals(juan, servicio.login("jlopez","qwerty"));

    }
    
}
