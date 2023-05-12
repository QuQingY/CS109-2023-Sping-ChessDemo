package view;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends ImagePanel {
//    private Color background;
private boolean influenced;

    public CellComponent(Color background, Point location, int size, String path) {
        super(path);
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
//        this.background = background;
    }

    public void setInfluenced(boolean influenced) {
        this.influenced = influenced;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
//        g.setColor(background);
        g.drawImage(getImageIcon().getImage(),1,1,this.getWidth()-1,this.getHeight()-1,this);
//        g.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
        if(this.influenced){
            g.setColor(Color.BLUE);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
        this.repaint();
    }
}
