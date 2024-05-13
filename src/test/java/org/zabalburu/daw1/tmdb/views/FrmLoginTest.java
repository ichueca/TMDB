/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package org.zabalburu.daw1.tmdb.views;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialOceanicTheme;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.zabalburu.daw1.tmdb.TMDB;
import org.zabalburu.daw1.tmdb.modelo.Usuario;
import org.zabalburu.daw1.tmdb.servicio.TMDBServicio;

/**
 *
 * @author ichueca
 */
public class FrmLoginTest {
    private FrameFixture window;
    private static TMDBServicio mock;

    public FrmLoginTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialOceanicTheme()));
            IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TMDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        FailOnThreadViolationRepaintManager.install();
        mock = Mockito.mock(TMDBServicio.class);
        
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        FrmLogin frame = GuiActionRunner.execute(() -> new FrmLogin());
        frame.setServicio(mock);
        this.window = new FrameFixture(frame);
        window.show(); // shows the frame to test*/
    }
    
    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }

    /**
     * Test of main method, of class FrmLogin.
     */
    @Test
    public void loginErroneo() {
        window.requireTitle("Login");
        
        Mockito.when(mock.login("jlopez", "12345"))
                .thenReturn(new Usuario());
        Mockito.when(mock.login("usuario", "12345"))
                .thenReturn(null);
        Mockito.when(mock.login("jlopez", "123455"))
                .thenReturn(new Usuario());
        
        // Usuario / password vacíos
        window.textBox("txtUsuario").setText("");
        window.textBox("pwdPassword").setText("");
        window.button("btnEntrar").click();
        window.label("lblError").requireText("Debe especificarse el usuario");
        window.textBox("txtUsuario").requireFocused();
        
        // Password vacía
        window.textBox("txtUsuario").setText("usuario");
        window.button("btnEntrar").click();
        window.label("lblError").requireText("Debe especificarse la contraseña");
        window.textBox("pwdPassword").requireFocused();
        
        // Usuario erróneo / pwd ok
        window.textBox("txtUsuario").setText("usuario");
        window.textBox("pwdPassword").setText("12345");
        window.button("btnEntrar").click();
        window.label("lblError").requireText("Usuario / contraseña erróneos");
        window.textBox("txtUsuario").requireFocused();
        
        // Usuario ok / pwd errónea
        window.textBox("pwdPassword").setText("123455");
        window.textBox("txtUsuario").setText("");
        window.textBox("txtUsuario").enterText("jlopez"); // Controlar tecla a tecla
        window.button("btnEntrar").click();
        window.label("lblError").requireText("Usuario / contraseña erróneos");
        window.textBox("txtUsuario").requireFocused();
        
    }
    
    @Test
    public void loginOk() throws Exception{
        try{
            int statusCode = catchSystemExit(() -> {
                Mockito.when(mock.login("jlopez", "12345"))
                .thenReturn(new Usuario());
                window.textBox("txtUsuario").enterText("jlopez"); // Emplea la propiedad name del control
                window.textBox("txtPassword").enterText("12345");
                window.button("btnEntrar").click();
                window.label("lblError").requireText("");
                window.optionPane().requireMessage("Bienvenido a la Aplicación");
            });
            Assertions.assertEquals(0, statusCode);
        } catch (java.lang.UnsupportedOperationException ex){}
    }
}
