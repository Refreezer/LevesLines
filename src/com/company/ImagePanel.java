package com.company;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel(String path) {
        try {
            var img = new File(path);
            image = ImageIO.read(img);
            if (img.delete())
                System.out.println(path + " deleted");
            else
                System.out.println(path + " not found");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }

}
