
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.tmdb.componentes;

import info.movito.themoviedbapi.model.movies.Cast;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author IñigoChueca
 */
public class RoundedShadowPane extends JPanel {

    private double cornerRadius = 20;
    private int shadowSize = 2;
    private Color shadowColor = Color.DARK_GRAY;
    private float shadowAlpha = 0.25f;

    private JComponent contentPane;

    public RoundedShadowPane() {
        setOpaque(false);
        int shadowSize = getShadowSize();
        int cornerRadius = (int) getCornerRadius() / 4;
        setBorder(new EmptyBorder(
                shadowSize + cornerRadius,
                shadowSize + cornerRadius,
                shadowSize + cornerRadius + shadowSize,
                shadowSize + cornerRadius + shadowSize
        ));
        setLayout(new BorderLayout());
        add(getContentPane());
    }

    public JComponent getContentPane() {
        if (contentPane == null) {
            contentPane = new JPanel();
        }
        return contentPane;
    }

    public void setContentPane(JComponent contentPane) {
        if (this.contentPane != null) {
            remove(this.contentPane);
        }
        this.contentPane = contentPane;
        this.setBackground(this.contentPane.getBackground());
        add(this.contentPane);
    }

    public double getCornerRadius() {
        return cornerRadius;
    }

    public int getShadowSize() {
        return shadowSize;
    }

    public Color getShadowColor() {
        return shadowColor;
    }

    public float getShadowAlpha() {
        return shadowAlpha;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        //g2d.setColor(Color.MAGENTA);
        //g2d.fillRect(0, 0, getWidth(), getHeight());

        double cornerRadius = getCornerRadius();
        double cornerInsets = cornerRadius / 4d;

        int width = getWidth();
        int height = getHeight();
        Insets insets = getInsets();
        insets.left -= cornerInsets;
        insets.right -= cornerInsets;
        insets.top -= cornerInsets;
        insets.bottom -= cornerInsets;

        width -= insets.left + insets.right;
        height -= insets.top + insets.bottom;

        RoundRectangle2D border = new RoundRectangle2D.Double(0, 0, width, height, cornerRadius, cornerRadius);
        ImageUtilities.applyQualityRenderingHints(g2d);
        g2d.drawImage(
                ImageUtilities.applyShadow(
                        border, getShadowSize(),
                        getBackground(),
                        getShadowColor(),
                        getShadowAlpha()
                ),
                insets.left,
                insets.top,
                this
        );
        g2d.setColor(Color.DARK_GRAY);
        g2d.translate(insets.left, insets.top);
        g2d.draw(border);
        g2d.dispose();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        JPanel p = new RoundedShadowPane();
        p.add(new JLabel("Un saludo!"));
        f.add(p);
        f.setSize(new Dimension(400, 300));
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}

class ImageUtilities {

    public static void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    public static BufferedImage createCompatibleImage(int width, int height) {
        return createCompatibleImage(width, height, Transparency.TRANSLUCENT);
    }

    public static BufferedImage createCompatibleImage(int width, int height, int transparency) {
        BufferedImage image = getGraphicsConfiguration().createCompatibleImage(width, height, transparency);
        image.coerceData(true);
        return image;
    }

    public static BufferedImage createCompatibleImage(BufferedImage image) {
        return createCompatibleImage(image, image.getWidth(), image.getHeight());
    }

    public static BufferedImage createCompatibleImage(BufferedImage image,
            int width, int height) {
        return getGraphicsConfiguration().createCompatibleImage(width, height, image.getTransparency());
    }

    public static GraphicsConfiguration getGraphicsConfiguration() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    public static BufferedImage generateMask(BufferedImage imgSource, Color color, float alpha) {
        int imgWidth = imgSource.getWidth();
        int imgHeight = imgSource.getHeight();

        BufferedImage imgBlur = createCompatibleImage(imgWidth, imgHeight);
        Graphics2D g2 = imgBlur.createGraphics();
        applyQualityRenderingHints(g2);

        g2.drawImage(imgSource, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
        g2.setColor(color);

        g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
        g2.dispose();

        return imgBlur;
    }

    public static BufferedImage generateBlur(BufferedImage imgSource, int size, Color color, float alpha) {
        //GaussianFilter filter = new GaussianFilter(size);

        int imgWidth = imgSource.getWidth();
        int imgHeight = imgSource.getHeight();

        BufferedImage imgBlur = createCompatibleImage(imgWidth, imgHeight);
        Graphics2D g2 = imgBlur.createGraphics();
        applyQualityRenderingHints(g2);

        g2.drawImage(imgSource, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
        g2.setColor(color);

        g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
        g2.dispose();

        //imgBlur = filter.filter(imgBlur, null);
        return imgBlur;
    }

    public static BufferedImage applyShadow(Shape shape, int size, Color backgroundColor, Color shadowColor, float alpha) {
        BufferedImage sourceImage = createCompatibleImage(shape.getBounds().width, shape.getBounds().height);
        Graphics2D g2d = sourceImage.createGraphics();
        applyQualityRenderingHints(g2d);
        g2d.translate(-shape.getBounds().getX(), -shape.getBounds().getY());
        g2d.setColor(backgroundColor);
        g2d.fill(shape);
        g2d.dispose();
        return applyShadow(sourceImage, size, shadowColor, alpha);
    }

    public static BufferedImage applyShadow(BufferedImage imgSource, int size, Color color, float alpha) {
        BufferedImage result = createCompatibleImage(imgSource, imgSource.getWidth() + (size * 2), imgSource.getHeight() + (size * 2));
        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(generateShadow(imgSource, size, color, alpha), size, size, null);
        g2d.drawImage(imgSource, 0, 0, null);
        g2d.dispose();

        return result;
    }

    public static BufferedImage generateShadow(BufferedImage imgSource, int size, Color color, float alpha) {
        int imgWidth = imgSource.getWidth() + (size * 2);
        int imgHeight = imgSource.getHeight() + (size * 2);

        BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight);
        Graphics2D g2 = imgMask.createGraphics();
        applyQualityRenderingHints(g2);

        int x = Math.round((imgWidth - imgSource.getWidth()) / 2f);
        int y = Math.round((imgHeight - imgSource.getHeight()) / 2f);
        g2.drawImage(imgSource, x, y, null);
        g2.dispose();

        // ---- Blur here ---
        BufferedImage imgGlow = generateBlur(imgMask, (size * 2), color, alpha);
        return imgGlow;
    }

    public static Image applyMask(BufferedImage sourceImage, BufferedImage maskImage) {
        return applyMask(sourceImage, maskImage, AlphaComposite.DST_IN);
    }

    public static BufferedImage applyMask(BufferedImage sourceImage, BufferedImage maskImage, int method) {
        BufferedImage maskedImage = null;
        if (sourceImage != null) {

            int width = maskImage.getWidth(null);
            int height = maskImage.getHeight(null);

            maskedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D mg = maskedImage.createGraphics();

            int x = (width - sourceImage.getWidth(null)) / 2;
            int y = (height - sourceImage.getHeight(null)) / 2;

            mg.drawImage(sourceImage, x, y, null);
            mg.setComposite(AlphaComposite.getInstance(method));

            mg.drawImage(maskImage, 0, 0, null);

            mg.dispose();
        }
        return maskedImage;
    }
}
