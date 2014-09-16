package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
	BufferedImage image;
	 
    public ImagePanel(String path)
    {
        
        // or load it in this class
        setLayout(null);
        // add components...
    }
 
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int x = 0;
        int y = 0;
        g.drawImage(image, x, y, this);
    }
}
