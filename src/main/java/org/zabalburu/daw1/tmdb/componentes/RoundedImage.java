/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.tmdb.componentes;

import info.movito.themoviedbapi.model.core.Movie;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.zabalburu.daw1.tmdb.servicio.TMDBServicio;

/**
 *
 * @author IñigoChueca
 */
public class RoundedImage extends JLabel{
    private Icon icon;
    private int borderSize = 0;
    private int anguloX = 10;
    private int anguloY = 10;
    
    public RoundedImage(){}
    
    public RoundedImage(int angulo){
        this.anguloX = angulo;
        this.anguloY = angulo;
    }
    
    public RoundedImage(int anguloX, int anguloY){
        this.anguloX = anguloX;
        this.anguloY = anguloY;
    }

    
    public Icon getIcon() {
        return icon;
    }

    
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }
    @Override
    protected void paintComponent(Graphics g) {
        
        if (icon != null){
            int ancho = getWidth();
            int alto = getHeight();
            int min = Math.min(ancho, alto);
            int x = (ancho - min) / 2;
            int y = (alto - min) / 2;
            int border = borderSize * 2;
            min -= border;
            Dimension size = getAutoSize(icon, min);
            BufferedImage img = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2_img = img.createGraphics();
            g2_img.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2_img.setColor(Color.red);
            g2_img.fillRoundRect(0, 0, min, min, anguloX, anguloY);
            Composite composite = g2_img.getComposite();
            g2_img.setComposite(AlphaComposite.SrcIn);
            g2_img.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2_img.drawImage(toImage(icon), 0, 0, size.width, size.height, null);
            g2_img.setComposite(composite);
            g2_img.dispose();
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (borderSize > 0){
                min += border;
                g2.setColor(getForeground());
                g2.fillRoundRect(x, y, min, min, anguloX, anguloY);
            }
            if (isOpaque()){
                g2.setColor(getBackground());
                min -= border;
                g2.fillRoundRect(x+borderSize, y+borderSize, min, min,  anguloX, anguloY);
            }
            g.drawImage(img, x+borderSize, y+borderSize, null);
            this.setMinimumSize(new Dimension(min,min));
            this.setPreferredSize(new Dimension(min,min));
        }
        //super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    private Image toImage(Icon icon){
        return ((ImageIcon) icon).getImage();
    }
    
    private Dimension getAutoSize(Icon image , int size){
        int w = size;
        int h = size;
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / iw;
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        if (width < 1){
            width = 1;
        }
        if (height < 1){
            height = 1;
        }
        return new Dimension(width, height);
    }
    
    public static void main(String[] args) {
        
        JFrame f = new JFrame();
        //f.setLayout(null);
        RoundedImage img = new RoundedImage(30,30);
        //img.setSize(new Dimension(100,100));
        //img.setIcon(new ImageIcon("images/juanLopez.jpg"));
        try {
            Movie pelicula = new TMDBServicio().getNowPlaying().get(0);
            URL url = new URI("https://image.tmdb.org/t/p/w342/"
                    + pelicula.getPosterPath()).toURL();
            img.setIcon(new ImageIcon(url));
            
            
        } catch (URISyntaxException ex) {
            Logger.getLogger(RoundedImage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RoundedImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        img.setForeground(Color.BLUE);
        //img.setBorderSize(2);
        img.setBackground(Color.red);
        f.add(img);
        f.setSize(new Dimension(150,150));
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
