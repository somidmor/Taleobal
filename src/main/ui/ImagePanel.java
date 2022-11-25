package ui;

import javax.swing.*;
import java.awt.*;


/* Represents a child class of jpanel for adding background image
 */
public class ImagePanel extends JPanel {
    private Image image = null;

    /*
     * EFFECTS: construct a Jpanel with an image as an input
     */
    public ImagePanel(Image image) {
        this.image = image;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }
}
