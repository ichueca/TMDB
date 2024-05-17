/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.tmdb.componentes;

import info.movito.themoviedbapi.model.core.Movie;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.zabalburu.daw1.tmdb.servicio.TMDBServicio;

/**
 *
 * @author IñigoChueca
 */
public class JLabelTransparent extends JLabel{
    private ImageIcon icon;
    private double alpha = 0.1;
    public JLabelTransparent(){
        
    }

    @Override
    public void setIcon(Icon icon) {
        this.icon = (ImageIcon) icon;
    }
    
    
    
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(icon.getIconWidth(), icon.getIconHeight());
    }
 
    public void setAlpha(double alpha)
    {
        this.alpha = alpha;
        repaint();
    }
 
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
 
        //  Paint background image
 
        Graphics2D g2 = (Graphics2D) g;
        /*int x = (getWidth() - icon.getIconWidth())/2;
        int y = (getHeight()- icon.getIconHeight())/2;
        g2.drawImage(icon.getImage(), x, y, this);*/
 
        //  Paint foreground image with appropriate alpha value
 
        Composite old = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha));
        int x = (getWidth() - icon.getIconWidth())/2;
        int y = (getHeight()- icon.getIconHeight())/2;
        g2.drawImage(icon.getImage(), x, y, this);
        g2.setComposite(old);
    }
    
    public static void main(String[] args) {
        
        JFrame f = new JFrame();
        //f.setLayout(null);
        JLabelTransparent img = new JLabelTransparent();
        //img.setSize(new Dimension(100,100));
        //img.setIcon(new ImageIcon("images/juanLopez.jpg"));
        try {
            Movie pelicula = new TMDBServicio().getNowPlaying().get(0);
            URL url = new URI("https://image.tmdb.org/t/p/w1280/"
                    + pelicula.getPosterPath()).toURL();
            img.setIcon(new ImageIcon(url));
            
            
        } catch (URISyntaxException ex) {
            Logger.getLogger(JLabelTransparent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(JLabelTransparent.class.getName()).log(Level.SEVERE, null, ex);
        }
        f.add(img);
        f.setSize(new Dimension(150,150));
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
