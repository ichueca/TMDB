/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.tmdb.panels;

import info.movito.themoviedbapi.model.movies.Cast;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialOceanicTheme;
import org.zabalburu.daw1.tmdb.componentes.RoundedShadowPane;
import org.zabalburu.daw1.tmdb.servicio.TMDBServicio;

/**
 *
 * @author IñigoChueca
 */
public class CastPanel extends JPanel {

    private List<Cast> cast = new ArrayList<>();

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
        initComponents();
    }

    public CastPanel() {

    }

    public CastPanel(List<Cast> cast) {
        this.cast = cast;
        initComponents();
    }
    
    

    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        Font fuente = new Font("Segoe UI", Font.PLAIN, 16);
        for (Cast c : cast) {
            JPanel pnl = new RoundedShadowPane();
            GridBagLayout gbl = new GridBagLayout();
            GridBagConstraints gbc = new GridBagConstraints();
            pnl.setLayout(gbl);
            pnl.setBackground(Color.WHITE);
            //this.setSize(new Dimension(100,300));
            JLabel lblCharacter = new JLabel(c.getCharacter());
            JLabel lblName = new JLabel(c.getOriginalName());
            JLabel lblProfile = new JLabel();
            URL url;
            try {
                url = new URI("https://image.tmdb.org/t/p/w185/"
                        + c.getProfilePath()).toURL();
                ImageIcon imag = new ImageIcon(url);
                imag.setImage(imag.getImage().getScaledInstance((int) (185 * 0.5), (int) (185 * 1.5 * 0.5), 
                        Image.SCALE_SMOOTH));
                lblProfile.setIcon(imag);
            } catch (URISyntaxException ex) {
                Logger.getLogger(CastPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(CastPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            pnl.removeAll();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbl.setConstraints(lblProfile, gbc);
            pnl.add(lblProfile);

            lblCharacter.setFont(fuente);
            lblCharacter.setHorizontalAlignment(JLabel.CENTER);
            lblCharacter.setForeground(Color.DARK_GRAY);
            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbl.setConstraints(lblCharacter, gbc);
            pnl.add(lblCharacter);
            lblName.setFont(fuente.deriveFont(12.f));
            lblName.setHorizontalAlignment(JLabel.CENTER);
            lblName.setForeground(Color.DARK_GRAY);
            gbc.gridy = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbl.setConstraints(lblName, gbc);
            pnl.add(lblName);
            this.setBorder(new EmptyBorder(10, 10, 10, 10));
            this.add(pnl);
            this.add(Box.createHorizontalStrut(10));
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialOceanicTheme()));
            IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CastPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFrame f = new JFrame();
        List<Cast> c = new TMDBServicio().getPelicula(
                new TMDBServicio().getPopular().get(0).getId())
                .getCredits()
                .getCast();
        CastPanel p = new CastPanel();
        f.setSize(new Dimension(800, 260));
        f.add(new JScrollPane(p));
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.setCast(c);
        f.setVisible(true);
        
    }
}
