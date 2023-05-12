package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class ChessComponent extends JComponent {
    private PlayerColor owner;

    private boolean selected;

    private boolean influenced;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isInfluenced() {
        return influenced;
    }

    public void setInfluenced(boolean influenced) {
        this.influenced = influenced;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(isInfluenced()){
            g.setColor(Color.GREEN);
            g.drawOval(0, 0, getWidth() , getHeight());
        }

    }

//    public ChessComponent(PlayerColor owner,int size){
//        this.selected=false;
//        this.owner=owner;
//        setSize(size/2, size/2);
//        setLocation(0,0);
//        setVisible(true);
//    }
}
