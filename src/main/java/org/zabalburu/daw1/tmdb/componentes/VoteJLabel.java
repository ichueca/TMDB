/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.tmdb.componentes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author ichueca
 */
public class VoteJLabel extends JLabel{

    public enum Tamaño {
        PEQUEÑO, GRANDE
    };
    private int porcentaje = 0;
    private Tamaño tamaño;

    public VoteJLabel() {
        this.tamaño = Tamaño.PEQUEÑO;
        //this.setText(texto);
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setTamaño(Tamaño tamaño) {
        this.tamaño = tamaño;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        Graphics2D g2d;
        if (g instanceof Graphics2D) {
            g2d = (Graphics2D) g;
            String texto = "" + porcentaje + "%";
            int anchoTexto = (int) g.getFontMetrics().getStringBounds(texto, g).getWidth();
            int ancho = this.getWidth() - anchoTexto;
            int alto = this.getHeight() + (int) g.getFontMetrics().getStringBounds(texto, g).getHeight() - 5;

            g2d.setColor(Color.BLACK);
            int stroke = this.tamaño == Tamaño.PEQUEÑO ? 2 : 4;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.fillOval(stroke, stroke, this.getWidth() - stroke * 2, this.getHeight() - stroke * 2);
            g2d.setColor(Color.WHITE);
            AttributedString text = new AttributedString(texto);
            //g2d.drawString(texto , ancho / 2 - 3, alto / 2);
            if (this.tamaño == Tamaño.PEQUEÑO) {
                text.addAttribute(TextAttribute.FONT, new Font("Arial", Font.BOLD, 12), 0, texto.length() - 1);
                text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, texto.length() - 1, texto.length());            //this.setFont(new Font("Arial",Font.BOLD,6));
                g2d.drawString(text.getIterator(), ancho / 2 + 5, alto / 2 - 2);
            } else {
                text.addAttribute(TextAttribute.FONT, new Font("Arial", Font.BOLD, 18), 0, texto.length() - 1);
                text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, texto.length() - 1, texto.length());            //this.setFont(new Font("Arial",Font.BOLD,6));
                g2d.drawString(text.getIterator(), ancho / 2, alto / 2);
            }

            if (porcentaje < 40) {
                g2d.setColor(Color.red);
            } else if (porcentaje < 75) {
                g2d.setColor(Color.yellow);
            } else {
                g2d.setColor(Color.green);
            }

            g2d.setStroke(new BasicStroke(stroke));

            g.drawArc(stroke, stroke, this.getWidth() - stroke * 2, this.getHeight() - stroke * 2, 90, (int) (-porcentaje * 3.6)); // (int) (porcentaje * 3.6) );
        }
    }

    public static void main(String[] args) {
        JFrame frm = new JFrame();
        frm.setLayout(null);
        frm.setSize(100, 100);
        //frm.setUndecorated(true);
        VoteJLabel lbl = new VoteJLabel();
        lbl.setOpaque(true);
        lbl.setTamaño(Tamaño.PEQUEÑO);
        lbl.setSize(new Dimension(30, 30));
        lbl.setLocation(0, 0);
        frm.add(lbl);
        VoteJLabel lbl2 = new VoteJLabel();
        lbl2.setOpaque(true);
        lbl2.setTamaño(Tamaño.GRANDE);
        lbl2.setSize(new Dimension(60, 60));
        lbl2.setLocation(40, 0);
        frm.add(lbl2);

        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
        lbl.setPorcentaje(7);
        lbl2.setPorcentaje(9);
    }
}
