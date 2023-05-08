package view;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image image;
    private ImageIcon imageIcon;

    public ImagePanel(String path){
        super();
        imageIcon = new ImageIcon(path);
        image = imageIcon.getImage();
    }


    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
    }

    public void setImage(String path) {
        imageIcon = new ImageIcon(path);
        this.image = imageIcon.getImage();
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }
}
