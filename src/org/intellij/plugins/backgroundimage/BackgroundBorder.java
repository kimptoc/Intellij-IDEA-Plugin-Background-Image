package org.intellij.plugins.backgroundimage;

import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * BackgroundBorder - border that holds the image to load into the background
 */
public class BackgroundBorder implements Border {

    /** Default image */
    private Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private boolean scale = NO_SCALE;
    public static final boolean SCALE = true;
    public static final boolean NO_SCALE = false;

    /**
     * Constructs a border that scales the image to fit the bound
     *
     * @param image
     */
    public BackgroundBorder(Image image) {
        this(image, SCALE);
    }

    /**
     *
     * @param image
     * @param scale
     */
    public BackgroundBorder(Image image, boolean scale) {
        this.scale = scale;
        this.image = image;
    }

    public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));

        //g2.drawImage(image, 1, 1, width, height, 1, 1, image.getWidth(), image.getHeight(), component);

        // only need to draw from x, y filling width, height

        for (int ix=x; ix<(x+width); ix+=image.getWidth(null))
        {
            for (int iy=y; iy<(y+height); iy+=image.getHeight(null))
            {
                g2.drawImage(image, ix, iy, component);
            }
        }

/*
        g2.drawImage(image, 0,0,component);
        g2.drawImage(image, 0, image.getHeight(), component);
        g2.drawImage(image, 0, 2*image.getHeight(), component);
        g2.drawImage(image, 0, 3*image.getHeight(), component);
        g2.drawImage(image, 0, 4*image.getHeight(), component);
*/

/*
        if (scale) {
            AffineTransform scale = new AffineTransform();
            //scale.scale(((double) width) / ((double) image.getWidth()), ((double) height) / ((double) image.getHeight()));
            double proportion = ((double) width) / ((double) image.getWidth());
            scale.scale(proportion, proportion);
            g2.drawImage(image, scale, component);
        } else {
            g2.drawImage(image, g2.getTransform(), component);
        }
*/
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
        return true;
    }

}