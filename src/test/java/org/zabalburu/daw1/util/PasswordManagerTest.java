/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package org.zabalburu.daw1.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ichueca
 */
public class PasswordManagerTest {
    
    public PasswordManagerTest() {
    }

    /**
     * Test of encriptarContraseña method, of class PasswordManager.
     */
    @Test
    public void testEncriptarContraseña() {
        String password = "12345";
        String hash1 = PasswordManager.encriptarContraseña(password);
        String hash2 = PasswordManager.encriptarContraseña(password);
        assertNotNull(hash1);
        assertNotNull(hash2);
        assertNotEquals(hash1, hash2, "Son iguales");
    }

    /**
     * Test of comprobarContraseña method, of class PasswordManager.
     */
    @Test
    public void testComprobarContraseña() {
        String password = "12345";
        String hash = PasswordManager.encriptarContraseña(password);
        assertTrue(PasswordManager.comprobarContraseña("12345", "$$2a$10$6DjRxCyoucVAIdCRGPx2Eelef31FnTz2e3D7A0ImOF1k7WOap2vHa"));
        assertFalse(PasswordManager.comprobarContraseña("123454", hash));
    }
    
}
