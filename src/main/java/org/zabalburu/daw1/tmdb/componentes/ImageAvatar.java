/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.tmdb.componentes;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author ichueca
 */
public class ImageAvatar extends JComponent{
    private Icon icon;
    private int borderSize = 2;

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
            g2_img.fillOval(0, 0, min, min);
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
                g2.fillOval(x, y, min, min);
            }
            if (isOpaque()){
                g2.setColor(getBackground());
                min -= border;
                g2.fillOval(x+borderSize, y+borderSize, WIDTH, HEIGHT);
            }
            g.drawImage(img, x+borderSize, y+borderSize, null);
        }
        super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
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
        ImageAvatar imag = new ImageAvatar();
        imag.setIcon(new ImageIcon("images/no_image.jpg"));
        imag.setForeground(Color.red);
        imag.setBorderSize(3);
        JFrame frm = new JFrame();
        frm.add(imag);
        frm.setSize(new Dimension(100,100));
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }
    
}
