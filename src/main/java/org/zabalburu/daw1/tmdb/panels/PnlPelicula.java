/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package org.zabalburu.daw1.tmdb.panels;

import info.movito.themoviedbapi.model.core.Movie;
import java.awt.Cursor;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialOceanicTheme;
import org.zabalburu.daw1.tmdb.componentes.VoteJLabel;
import org.zabalburu.daw1.tmdb.servicio.TMDBServicio;
import org.zabalburu.daw1.tmdb.views.FrmDetallePelicula;

/**
 *
 * @author ichueca
 */
public class PnlPelicula extends javax.swing.JPanel {
    private TMDBServicio servicio = new TMDBServicio();
    private Movie pelicula;

    public Movie getPelicula() {
        return pelicula;
    }

    public void setPelicula(Movie pelicula) {
        this.pelicula = pelicula;
    }

    /**
     * Creates new form PnlPelicula
     */
    public PnlPelicula() {
        initComponents();
    }

    public PnlPelicula(Movie movie) {

        this.pelicula = movie;
        initComponents();
        lblTitulo.setText("<html><center>" + this.pelicula.getTitle() + "</center></html>");
        try {
            URL url = new URI("https://image.tmdb.org/t/p/w92/"
                    + this.pelicula.getPosterPath()).toURL();
            lblPoster.setIcon(new ImageIcon(url));
            lblVotos.setPorcentaje(this.pelicula.getVoteAverage().intValue() * 10);
        } catch (URISyntaxException ex) {
            Logger.getLogger(PnlPelicula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PnlPelicula.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblVotos = new org.zabalburu.daw1.tmdb.componentes.VoteJLabel();
        lblPoster = new javax.swing.JLabel();

        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setMaximumSize(new java.awt.Dimension(120, 230));
        setMinimumSize(new java.awt.Dimension(120, 230));
        setPreferredSize(new java.awt.Dimension(120, 231));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        setLayout(null);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        add(lblTitulo);
        lblTitulo.setBounds(0, 150, 110, 60);

        lblVotos.setText("voteJLabel1");
        lblVotos.setPorcentaje(15);
        add(lblVotos);
        lblVotos.setBounds(60, 120, 30, 30);
        add(lblPoster);
        lblPoster.setBounds(0, 0, 92, 138);
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        new FrmDetallePelicula(servicio.getPelicula(pelicula.getId()))
                .setVisible(true);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_formMousePressed

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(
                    new MaterialOceanicTheme()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JFrame frm = new JFrame();
        frm.setSize(100, 240);

        Movie movie = new TMDBServicio().getNowPlaying().get(0);
       
        PnlPelicula peli = new PnlPelicula(movie);
        frm.add(peli);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblPoster;
    private javax.swing.JLabel lblTitulo;
    private org.zabalburu.daw1.tmdb.componentes.VoteJLabel lblVotos;
    // End of variables declaration//GEN-END:variables
}
