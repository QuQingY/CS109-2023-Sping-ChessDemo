package view.Animal;


import model.PlayerColor;
import view.ChessComponent;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ElephantChessComponent extends ChessComponent {
    private PlayerColor owner;

    private boolean selected;

    public ElephantChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(owner==PlayerColor.RED){
            setImageIcon("D:\\JavaProject\\动物1\\象.png");
            g.drawImage(getImageIcon().getImage(),0,0,getWidth(),getHeight(),this);
        }else if (owner==PlayerColor.BLUE) {
            setImageIcon("D:\\JavaProject\\动物1\\StompingElephantIdleSide.gif");
            g.drawImage(getImageIcon().getImage(),0,0,getWidth(),getHeight(),this);
        }
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
//        g2.setFont(font);
//        g2.setColor(owner.getColor());
//        g2.drawString("象", getWidth() / 4, getHeight() * 5 / 8); // FIXME: Use library to find the correct offset.
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
